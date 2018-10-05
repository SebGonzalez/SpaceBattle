package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class WindowGame extends BasicGame {

	private GameContainer container;
	private TiledMap map;

	private Personnage p;

	private Animation[] animations = new Animation[8];
	
	public WindowGame() {
		super("SpaceBattle");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		this.container = container;
		container.setAlwaysRender(true);
		this.map = new TiledMap("ressources/map/exemple-collision.tmx");
		SpriteSheet spriteSheet = new SpriteSheet("ressources/sprites/character.png", 64, 64);
		this.animations[0] = loadAnimation(spriteSheet, 0, 1, 0);
		this.animations[1] = loadAnimation(spriteSheet, 0, 1, 1);
		this.animations[2] = loadAnimation(spriteSheet, 0, 1, 2);
		this.animations[3] = loadAnimation(spriteSheet, 0, 1, 3);
		this.animations[4] = loadAnimation(spriteSheet, 1, 9, 0);
		this.animations[5] = loadAnimation(spriteSheet, 1, 9, 1);
		this.animations[6] = loadAnimation(spriteSheet, 1, 9, 2);
		this.animations[7] = loadAnimation(spriteSheet, 1, 9, 3);

		p = new Personnage();
	}

	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		g.translate(container.getWidth() / 2 - (int) p.getxCamera(), container.getHeight() / 2 - (int)p.getyCamera());

		this.map.render(0, 0, 0);
		this.map.render(0, 0, 1);
		this.map.render(0, 0, 2);
		
		g.setColor(new Color(0, 0, 0, .5f));
		g.fillOval(p.getX() - 16, p.getY() - 8, 32, 16);
		g.drawAnimation(animations[p.getDirection() + (p.isMoving() ? 4 : 0)], p.getX() - 32, p.getY() - 60);
		
		this.map.render(0, 0, 3);
	    this.map.render(0, 0, 4);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		if (p.isMoving()) {
			float futurX = p.getX();
			float futurY = p.getY();
			switch (p.getDirection()) {
			case 0:
				futurY = p.getY() - .1f * delta;
				break;
			case 1:
				futurX = p.getX() - .1f * delta;
				break;
			case 2:
				futurY = p.getY() + .1f * delta;
				break;
			case 3:
				futurX = p.getX() + .1f * delta;
				break;
			}
			Image tile = this.map.getTileImage((int) futurX / this.map.getTileWidth(),
					(int) futurY / this.map.getTileHeight(), this.map.getLayerIndex("logic"));
			// il y a colision si la tuile "tuture" existe
			boolean collision = tile != null;
			if (collision) {
				Color color = tile.getColor((int) futurX % this.map.getTileWidth(),
						(int) futurY % this.map.getTileHeight());
				collision = color.getAlpha() > 0;
			}

			if (collision) {
				p.setMoving(false);
			} else {
				p.setX(futurX);
				p.setY(futurY);
			}

		}

		int w = container.getWidth() / 4;
		if (p.getX() > p.getxCamera() + w)
			p.setxCamera(p.getX() - w);
		if (p.getX() < p.getxCamera() - w)
			p.setxCamera(p.getX() + w);
		int h = container.getHeight() / 4;
		if (p.getY() >p.getyCamera() + h)
			p.setyCamera(p.getY() - h);
		if (p.getY() < p.getyCamera() - h)
			p.setyCamera(p.getY() + h);

	}

	@Override
	public void keyReleased(int key, char c) {
		p.setMoving(false);
		;
		if (Input.KEY_ESCAPE == key) {
			container.exit();
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		switch (key) {
		case Input.KEY_UP:
			p.setDirection(0);
			p.setMoving(true);
			break;
		case Input.KEY_LEFT:
			p.setDirection(1);
			p.setMoving(true);
			break;
		case Input.KEY_DOWN:
			p.setDirection(2);
			p.setMoving(true);
			break;
		case Input.KEY_RIGHT:
			p.setDirection(3);
			p.setMoving(true);
			break;
		}
	}

	private Animation loadAnimation(SpriteSheet spriteSheet, int startX, int endX, int y) {
		Animation animation = new Animation();
		for (int x = startX; x < endX; x++) {
			animation.addFrame(spriteSheet.getSprite(x, y), 100);
		}
		return animation;
	}

	public static void main(String[] args) throws SlickException {
		new AppGameContainer(new WindowGame(), 800, 600, false).start();
	}

}
