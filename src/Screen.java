import javax.swing.*;
import java.awt.*;
import java.awt.image.*; // for image
import java.io.*; // for save data
import sun.audio.*
;
import java.awt.event.*;


public class Screen extends JPanel implements Runnable {
	public Thread thread = new Thread (this);
	
	public static Image[] tileset_ground = new Image[100];// to make animation
	public static Image[] tileset_air = new Image[100];// to make animation
	public static Image[] tileset_res = new Image[100]; // to draw animation
	public static Image[] tileset_mob = new Image[100]; // draw mob
	
	
	public static int myWidth, myHeight;
	public static int coinage = 10;
	public static int killed = 0 , killsToWin=0, level =1, maxLevel =3 ;
	public static int winTime= 4000, winFrame=0;
	public static int health = 100;
	public static boolean isFirst = true;
	public static boolean isDebug = false;
	public static boolean isWin=false;
	public static Point mse= new Point(0,0);
	
	public static Room room;
	public static Save save;
	public static Store store;
	public static Mob[] mobs = new Mob[100];
	public Screen(Frame frame) {
		frame.addMouseListener(new KeyHandel());
		frame.addMouseMotionListener(new KeyHandel());
		thread.start();
	}
	/*public static class AL implements ActionListener{
		public final void actionPerformed(ActionEvenet e){
			music();
		}
	}
	public static void music(){
		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD;
		ContinuousAudioDataStream loop = null ;
		
		try{
		BGM = new AudioStream(new FileInputStream("Shingeki no Kyojin Opening OP Guren no Yumiya Linked Horizon Extended Ver.mp3"));
		System.out.println("hi");
		MD = BGM.getData();
		loop = new ContinuousAudioDataStream(MD);
		}catch(IOException error){}
		MGP.start(loop);
	}*/
	
	public static void hasWon(){
		if(killed== killsToWin){
			isWin=true;
			killed =0;
			coinage =0;
		}
	}
	public void define(){
		room = new Room();
		save = new Save();
		store = new Store();
		
		coinage = 10;
		health = 100;
		
		for(int i=0;i<tileset_ground.length;i++){
			
			tileset_ground[i]=new ImageIcon("Rescourses/tileset_ground.png").getImage();
			tileset_ground[i]=createImage(new FilteredImageSource(tileset_ground[i].getSource(),new CropImageFilter(0,26*i,26,26)));
		}
		for(int i=0;i<tileset_air.length;i++){
			
			tileset_air[i]=new ImageIcon("Rescourses/tileset_air.png").getImage();
			tileset_air[i]=createImage(new FilteredImageSource(tileset_air[i].getSource(),new CropImageFilter(0,26*i,26,26)));
		}
		
		tileset_res[0]= new ImageIcon("Rescourses/cell.png").getImage();
		tileset_res[1]= new ImageIcon("Rescourses/Heart.png").getImage();
		tileset_res[2]= new ImageIcon("Rescourses/Coin.png").getImage();
		
		tileset_mob[0] = new ImageIcon("Rescourses/mob001.png").getImage();
		
		save.loadSave(new File("save/data"+level+".ulixava"));
		
		for(int i=0; i<mobs.length;i++){
			mobs[i] = new Mob();
		   
			
		}
	}
	
	public void paintComponent(Graphics g){
		if(isFirst){
			
			myWidth = getWidth();
			myHeight= getHeight();
			define();// makes blocks go wrong side 
			
			
			isFirst =false;
		}
		g.setColor(new Color(86 ,98,137));
		
		g.fillRect(0,0,getWidth(), getHeight());
		g.setColor(new Color (0,0,0));
		g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y+ room.blockSize);//Draw the left line.
		g.drawLine(room.block[0][room.worldWidth-1].x+ room.blockSize, 0, room.block[0][room.worldWidth-1].x+ room.blockSize, room.block[room.worldHeight-1][0].y+ room.blockSize);// Draw the right line.
		g.drawLine(room.block[0][0].x,room.block[room.worldHeight-1][0].y+ room.blockSize,room.block[0][room.worldWidth-1].x+room.blockSize,room.block[room.worldHeight-1][0].y+ room.blockSize);//Drawing the Bottom Line
		
		room.draw(g); // Drawing the room 
		
		for (int i=0;i<mobs.length;i++ ){
			if(mobs[i].inGame){
				mobs[i].draw(g);
			}
		}
		
		store.draw(g); // drawing the store
		
		if(health <1){
			g.setColor(new Color(240,20,20));
			g.fillRect(0,0, myWidth ,myHeight);
			g.setColor(new Color(255,255,255));
			g.setFont(new Font("Courier New",Font.BOLD,14));
			g.drawString("Game Over, Unlucky...",10,20);
		}
		if(isWin){
			g.setColor(new Color(255,255,255));
			g.fillRect(0,0,getWidth(),getHeight());
			g.setColor(new Color(0,0,0));
			g.setFont(new Font("Courier New",Font.BOLD,14));
			if(level == maxLevel){
				g.drawString("Humanity have won against the Purplolarz ! You may now close the window and enjoy the peace ! ",10,20);
			}else{
				g.drawString("Humanity won the Gate ! Please wait for the next Gate",10,20);
			}
			
			}
	}
	public int spawnTime= 1000, spawnFrame =0 ;  // MOb FLOW !!!
	public void mobSpawner(){
		if(spawnFrame >=spawnTime){
			for(int i=0;i<mobs.length;i++){
				if(!mobs[i].inGame){
					mobs[i].spawnMob(Value.mobGreeny);
					break;
					}
			}
			spawnFrame=0;
		}else{
			spawnFrame+=1;
		}
	}

	public void run(){
		while(true){
			if(!isFirst && health >0 &&  !isWin){
				room.physic();
				mobSpawner();
				/*game mechanics part*/
				for(int i=0;i<mobs.length;i++){
					if(mobs[i].inGame){
						mobs[i].physic();
							
						
					}
				}
			} else {
				if(isWin){
					if(winFrame>= winTime){
						if(level==maxLevel){
							System.exit(0);
						}else{
							level +=1;
							define();
							isWin=false;
							
						}
						winFrame=0;
					}else{
						winFrame+=1;
					}
				}
			}
			repaint();
			
			try{
				Thread.sleep(1);
			} catch(Exception e) { }
			
		}
	}
}
