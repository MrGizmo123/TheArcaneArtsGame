package Game.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import Game.Models.Model;
import Game.Models.ModelData;
import Game.Models.ModelTexture;
import Game.Models.OBJLoader;
import Game.Models.TexturedModel;

public class Loader {

	private List<Integer> vaos;
	private List<Integer> vbos;
	private List<Integer> textures;
	
	public Loader(){
		vaos = new ArrayList<Integer>();
		vbos = new ArrayList<Integer>();
		textures = new ArrayList<Integer>();
	}
	
	public Model loadToVAO(int[] indices, float[] positions, float[] normals){
		int vao = createVAO();
		store(0, 3, positions);
		store(1, 3, normals);
		storeIndeces(indices);
		GL30.glBindVertexArray(0);
		return new Model(vao,indices.length);
	}
	
	public Model loadToVAO(int[] indices, float[] positions, float[] textureCoords, float[] normals) {
		int vao = createVAO();
		store(0, 3, positions);
		store(1, 2, textureCoords);
		store(2, 3, normals);
		storeIndeces(indices);
		return new Model(vao, indices.length);
	}
	
	public int createEmptyVbo(int floatCount) {
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, floatCount * 4, GL15.GL_DYNAMIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return vbo;
	}
	
	public void addAttrib(int vao, int vbo, int attrib, int elementSize)
	{
		GL30.glBindVertexArray(vao);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL20.glVertexAttribPointer(attrib, elementSize, GL11.GL_FLOAT,false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL30.glBindVertexArray(0);
	}
	
	public void addInstancedAttribf(int vao, int vbo, int attrib, int dataSize, int stride, int offset) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attrib, dataSize, GL11.GL_FLOAT, false, stride * 4, offset * 4);
	    GL33.glVertexAttribDivisor(attrib, 1);
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	    GL30.glBindVertexArray(0);
	}
	
	public void addInstancedAttribi(int vao, int vbo, int attrib, int dataSize, int stride, int offset) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL30.glBindVertexArray(vao);
		GL20.glVertexAttribPointer(attrib, dataSize, GL11.GL_INT, false, stride * 4, offset * 4);
	    GL33.glVertexAttribDivisor(attrib, 1);
	    GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	    GL30.glBindVertexArray(0);
	}
	
	public void updateVbof(int vbo, float[] data) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = createFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_DYNAMIC_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public void updateVboi(int vbo, int[] data) {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		IntBuffer buffer = createIntBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.capacity() * 4, GL15.GL_DYNAMIC_DRAW);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, buffer);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public Model loadToVAO(int[] indices, float[] positions) {
		int vao = createVAO();
		store(0, 3, positions);
		storeIndeces(indices);
		GL30.glBindVertexArray(0);
		return new Model(vao, indices.length);
	}
	
	public int loadToVAO3D(float[] positions, float[] textureCoords) {
		int vao = createVAO();
		store(0, 3, positions);
		store(1, 2, textureCoords);
		GL30.glBindVertexArray(0);
		return vao;
	}
	
	public int createVAO(){
		int vao = GL30.glGenVertexArrays();
		vaos.add(vao);
		GL30.glBindVertexArray(vao);
		return vao;
	}
	
	private void store(int attrib,int size,float[] data){
		int vbo = GL15.glGenBuffers();
		vbos.add(vbo);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = createFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, size, GL11.GL_FLOAT,false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	public FloatBuffer createFloatBuffer(float[] data){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public void cleanUp(){
		for(int vao : vaos){
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo : vbos){
			GL15.glDeleteBuffers(vbo);
		}
		for(int texture : textures){
			GL11.glDeleteTextures(texture);
		}
	}


	public static int loadTexture(String fileName){

		//load png file
		PNGDecoder decoder = null;
		try {
			decoder = new PNGDecoder(new FileInputStream(new File("res\\" + fileName + ".png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//create a byte buffer big enough to store RGBA values
		ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());

		//decode
		try {
			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//flip the buffer so its ready to read
		buffer.flip();

		//create a texture
		int id = GL11.glGenTextures();

		//bind the texture
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

		//tell opengl how to unpack bytes
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

		//set the texture parameters, can be GL_LINEAR or GL_NEAREST
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		//upload texture
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		// Generate Mip Map
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

		if(GL.getCapabilities().GL_EXT_texture_filter_anisotropic){
			float amount = Math.min(4f, EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, amount);
		}else{
			System.out.println("anisotropic filtering not supported");
		}

		return id;
	}
	
	private void storeIndeces(int[] indeces){
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = createIntBuffer(indeces);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	public IntBuffer createIntBuffer(int[] data){
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	public TexturedModel loadTexturedModel(String modelName, String texture) {
		ModelData mData = OBJLoader.loadOBJ(modelName);
		Model model = loadToVAO(mData.getIndices(), mData.getVertices(), mData.getTextureCoords(), mData.getNormals());
		TexturedModel txm = new TexturedModel(model, loadTexture(texture));
		return txm;
	}
	
}
