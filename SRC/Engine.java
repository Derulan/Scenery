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
		if(mainCameraTexture!=null) g.drawImage(mainCameraTexture, 0, 0, null);
	}

	public static void main(String[] args) throws InterruptedException 
	{
		new PinaCollada();

		
		int width = 640;
		int height = 480;
		boolean realtime = false;
		boolean keepRender = true;
		JFrame frame = new JFrame("Test Display");
		Engine engine = new Engine();
		frame.add(engine);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		Scene local = new Scene();
		local.renderer.material.color = new Color(245, 179, 93);
		local.ambientLight = 0.3;

		/*
		SceneObject tobj = new SceneObject(local, "tobjign", new Vector3());
		SphereRenderer rend = tobj.AddComponent(SphereRenderer.class);
		rend.radius = 60.0;
		rend.material = new Material(new Color(255, 255, 255), LambertianShader.class);

		SceneObject pog = new SceneObject(local, "Yeeters", new Vector3(0,0,90));
		SphereRenderer rend2 = pog.AddComponent(SphereRenderer.class);
		rend2.radius = 30.0;
		rend2.material = new Material(new Color(125, 125, 255), LambertianShader.class);

		SceneObject firstTri = new SceneObject(local, "Bean", new Vector3());
		TriangleRenderer rend3 = firstTri.AddComponent(TriangleRenderer.class);
		rend3.vertex1 = new Vector3(0, 0, 0);
		rend3.vertex2 = new Vector3(200, 0, 0);
		rend3.vertex3 = new Vector3(100, 125, 0);
		rend3.material = new Material(Color(125, 125, 255), LambertianShader.class);
		*/

		SceneObject cubeTest = new SceneObject(local, "Yump", new Vector3(0, 55, 0));
		cubeTest.transform.scaleTo(new Vector3(100,100,100));
		MeshRenderer rend4 = cubeTest.AddComponent(MeshRenderer.class);
		rend4.mesh = PinaCollada.imported_meshes.get("MONKEY");
		rend4.materials = new Material[]{new Material(new Color(53, 117, 48), LambertianShader.class)};
		rend4.material = rend4.materials[0];
		


		SceneObject mc = new SceneObject(local, "Main Camera", new Vector3());
		Camera comp = mc.AddComponent(Camera.class);
		comp.resize(width, height);

		SceneObject ml = new SceneObject(local, "Directional Light", new Vector3());
		DirectionalLight epic = ml.AddComponent(DirectionalLight.class);
		epic.direction = new Vector3(0.5, -1.0, -0.5);


		if(realtime)
		{
			while(true)
			{
				mainCameraTexture = comp.render();
				engine.repaint();
				Thread.sleep(1);
			}
		}
		else
		{
			mainCameraTexture = comp.render();
			engine.repaint();
			File output = new File("..\\Render\\SceneryRender.png");
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
