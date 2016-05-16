package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Kevin on 16/05/2016.
 */
public abstract class CharacterEntity implements Entity {
	Body body;
	Fixture fixture, footSensor;
	Animation aniIdle, aniMove;
	boolean bRight;    // If the character is facing right, facing left otherwise
	boolean isIdle;    // If the character isn't moving (play idle animation)
	boolean isGrounded; // If the character is on the ground
	float animationTime;

	CharacterEntity(World world, Vector2 position, String animationName) {
		animationTime = 0f;
		bRight = true;
		isIdle = true;
		isGrounded = false;

		getAnimations(animationName);
		createBody(world, position);
		createFixture();
		createFootSensor();
	}

	// Let implementing clases define 'update' for thinking and movement

	public void draw(SpriteBatch spriteBatch) {
		// drawing sprite on main body using default library, not using animatedbox2dsprite because it doesn't loop the animation
		animationTime++;
		float x = body.getPosition().x;
		float y = body.getPosition().y;

		TextureRegion textureRegion;

		if (isIdle)
			textureRegion = aniIdle.getKeyFrame(animationTime, true);

		else
			textureRegion = aniMove.getKeyFrame(animationTime, true);

		int width = textureRegion.getRegionWidth() / 2;
		int height = textureRegion.getRegionHeight() / 2;

		if (bRight)
			spriteBatch.draw(textureRegion, x - (width / 2f), y - (height / 2f), width, height);

		else
			spriteBatch.draw(textureRegion, x + (width / 2f), y - (height / 2f), -width, height);
	}

	public void jump(float jumpVelocity) {
		body.setLinearVelocity(body.getLinearVelocity().x, jumpVelocity);
	}

	public void stop() {
		body.setLinearVelocity(0f, body.getLinearVelocity().y);
	}

	private void getAnimations(String animationName) {
		TextureAtlas taIdle = new TextureAtlas(Gdx.files.internal(animationName + "/idle/idle.pack"));
		TextureAtlas taRun = new TextureAtlas(Gdx.files.internal(animationName + "/run/run.pack"));

		aniIdle = new Animation(taIdle.getRegions().size, taIdle.getRegions());
		aniMove = new Animation(taRun.getRegions().size, taRun.getRegions());
	}

	private void createBody(World world, Vector2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(new Vector2(position.x / 2f, position.y / 2f));
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.fixedRotation = true; // Always upright
		body = world.createBody(bodyDef);
	}

	private void createFixture() {
		int width = aniIdle.getKeyFrame(0f).getRegionWidth() / 2;
		int height = aniIdle.getKeyFrame(0f).getRegionHeight() / 2;

		PolygonShape shape = new PolygonShape();
		FixtureDef fixtureDef = new FixtureDef();
		shape.setAsBox(width / 2f, height / 2f);
		fixtureDef.shape = shape;

		fixture = body.createFixture(fixtureDef);
	}

	private void createFootSensor() {
		TextureRegion trPlayer = aniIdle.getKeyFrame(0f);
		int width = trPlayer.getRegionWidth() / 2, height = trPlayer.getRegionHeight() / 2;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2f, 0.1f, new Vector2(body.getLocalCenter().x, body.getLocalCenter().y - height / 2f), 0f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = true;

		footSensor = body.createFixture(fixtureDef);
	}
}
