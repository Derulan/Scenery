import java.awt.Graphics;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.*;

@SuppressWarnings("serial")
public class Engine extends JPanel {

	public static BufferedImage mainCameraTexture;

	@Override
	public void paint(Graphics g) 
	{
		super.paint(g);
		if(mainCameraTexture != null) g.drawImage(mainCameraTexture, 0, 0, null);
	}

	public static void main(String[] args) throws InterruptedException 
	{
		//Setting up renderer
		new PinaCollada();
		int width = 640;
		int height = 480;
		int time = 0;
		boolean realtime = true;
		JFrame frame = new JFrame("Scenery");
		Engine engine = new Engine();
		frame.add(engine);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Test Scene
		Scene local = new Scene();
		local.renderer.material.color = new Color(245, 179, 93);
		local.renderer.material.shader = SkyboxBasicShader.class;
		local.ambientLight = 0.2;
		
		Material greenTestMaterial = new Material(new Color(15, 160, 60), LambertianShader.class);
		
		SceneObject sphere = new SceneObject(local, "Sphere", new Vector3(0, 32, 0));
		SphereRenderer rend = sphere.AddComponent(SphereRenderer.class);
		rend.radius = 30.0;
		rend.material = greenTestMaterial;

		SceneObject triangle = new SceneObject(local, "Triangle", new Vector3());
		TriangleRenderer rend3 = triangle.AddComponent(TriangleRenderer.class);
		rend3.vertex1 = new Vector3(125, -15, 0);
		rend3.vertex2 = new Vector3(250, -15, 0);
		rend3.vertex3 = new Vector3(188, 90, 0);
		rend3.material = greenTestMaterial;
		
		SceneObject monkey = new SceneObject(local, "Monkey", new Vector3(-200, 55, 0));
		monkey.transform.scaleTo(new Vector3(50,50,50));
		MeshRenderer rend4 = monkey.AddComponent(MeshRenderer.class);
		rend4.mesh = PinaCollada.imported_meshes.get("MONKEY");
		rend4.materials = new Material[]{greenTestMaterial};
		rend4.material = rend4.materials[0];

		SceneObject ml = new SceneObject(local, "Directional Light", new Vector3());
		DirectionalLight dlight = ml.AddComponent(DirectionalLight.class);
		dlight.direction = new Vector3(0.5, -1.0, -0.5);


		//Space Scene
		Scene space = new Scene();
		space.renderer.material.shader = WierdSkyboxShader.class;
		space.ambientLight = 0.3;

		SceneObject spaceship = new SceneObject(space, "Spaceship", new Vector3(0, 55, 0));
		spaceship.transform.scaleTo(new Vector3(40,40,40));
		MeshRenderer rend5 = spaceship.AddComponent(MeshRenderer.class);
		rend5.mesh = PinaCollada.imported_meshes.get("RWING");
		rend5.materials = new Material[]{new Material(new Color(200,200,200), LambertianShader.class), new Material(new Color(0, 0, 200), LambertianShader.class), new Material(new Color(20, 20, 20), LambertianShader.class)};
		rend5.material = rend5.materials[0];

		SceneObject sl = new SceneObject(space, "Directional Light", new Vector3());
		DirectionalLight dlight2 = sl.AddComponent(DirectionalLight.class);
		dlight2.direction = new Vector3(0.5, -1.0, -0.5);


		//Point Light animated scene
		Scene point = new Scene();
		point.ambientLight = 0.1;

		SceneObject sphere1 = new SceneObject(point, "Sphere1", new Vector3(-200, 32, 0));
		SphereRenderer rendS1 = sphere1.AddComponent(SphereRenderer.class);
		rendS1.radius = 45.0;
		rendS1.material = new Material(new Color(255, 0, 255), LambertianShader.class);

		SceneObject sphere2 = new SceneObject(point, "Sphere2", new Vector3(0, 32, 0));
		SphereRenderer rendS2 = sphere2.AddComponent(SphereRenderer.class);
		rendS2.radius = 30.0;
		rendS2.material = new Material(new Color(255, 255, 0), LambertianShader.class);

		SceneObject sphere3 = new SceneObject(point, "Sphere3", new Vector3(200, 32, 0));
		SphereRenderer rendS3 = sphere3.AddComponent(SphereRenderer.class);
		rendS3.radius = 45.0;
		rendS3.material = new Material(new Color(0, 255, 255), LambertianShader.class);

		SceneObject pl = new SceneObject(point, "Point Light", new Vector3(0, 0, 100));
		SphereRenderer lightBody = pl.AddComponent(SphereRenderer.class);
		lightBody.radius = 7d;
		lightBody.material = new Material(new Color(255, 255, 255), UnlitShader.class);
		pl.AddComponent(PointLight.class);


		//The ONLY camera
		SceneObject mc = new SceneObject(point, "Main Camera", new Vector3(0, 0, 200));
		Camera comp = mc.AddComponent(Camera.class);
		comp.resize(width, height);
		
		if(realtime)
		{
			while(true)
			{
				pl.transform.moveTo(new Vector3(0, 200*Math.sin((double)time * 0.05), 100));
				mainCameraTexture = comp.render();
				engine.repaint();
				Thread.sleep(1);
				if(time < Integer.MAX_VALUE) time++;
				else time = 0;
			}
		}
		else
		{
			mainCameraTexture = comp.render();
			engine.repaint();
			File output = new File("Render\\SceneryRender.png");
			try
			{
				ImageIO.write(mainCameraTexture, "png", output);
			}
			catch(IOException error)
			{
				System.out.println("Could not write render to file.");
			}
		}
	}
}
