package scene;

import elements.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * Scene class: a class to represent a scene which will be the base for the
 * picture
 */
public class Scene {
	/**
	 * The scene needs a name
	 */
	public String name;
	/**
	 * The base is black
	 */
	public Color background = Color.BLACK;
	/**
	 * Definition of the ambient light for the background
	 */
	public AmbientLight ambientLight = new AmbientLight(background, 0);
	/**
	 * All the geometric shapes that exist in the scene
	 */
	public Geometries geometries;

	/**
	 * Constructor gets the name of the scene
	 * 
	 * @param name – string. In order to name the scene.
	 */
	public Scene(String name) {
		this.name = name;
		this.geometries = new Geometries();
	}

	/**
	 * A function to have a background other than the default of black
	 * 
	 * @param background – the new color needed to color the background
	 * @return the scene itself – with an updated background.
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * A function to change the ambientLight.
	 * 
	 * @param ambientLight setting new parameters for the ambient light
	 * @return the scene, but with different ambient lighting.
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * A function to change the geometries in the scene.
	 * 
	 * @param geometries - setting new shapes for the background
	 * @return the scene, but with different shapes.
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}

}