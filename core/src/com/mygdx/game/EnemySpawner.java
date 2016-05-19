package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by Rueban Rasaselvan on 03/04/2016.
 */

// K: For what it's doing, this should really be called something along the lines of 'EnemyManager'

public class EnemySpawner implements Entity {
	World world;
	Vector2 position;
	ArrayList<FastEnemy> fastEnemies;
	int spawnTimer;
	int maxEnemies;
	Player player;

	EnemySpawner(World world, Player player, Vector2 position, int maxEnemies) {
		this.world = world;
		this.player = player;
		this.position = position;
		this.maxEnemies = maxEnemies;
		spawnTimer = 0;

		fastEnemies = new ArrayList<FastEnemy>();
	}

	void createEnemy() {
		fastEnemies.add(new FastEnemy(world, player, position));
	}

	public void draw(SpriteBatch spriteBatch) {
		//only the enemies that have already been spawned in are updated
		for (FastEnemy fastEnemy : fastEnemies) {
			fastEnemy.draw(spriteBatch);
		}
	}

	public void update() {
		float playerDist = player.getPosition().dst(position);
		//only if the player enters within the region of radius 500 from the spawner block
		//then the enemies will spawn.
		spawnTimer++;
		if (playerDist <= 500f && fastEnemies.size() < maxEnemies) {
			//every 5 seconds a new enemy is spawned
			if (spawnTimer >= 60) {
				spawnTimer = 0;
				createEnemy();
			}
		}

		for (FastEnemy fastEnemy : fastEnemies) {
			fastEnemy.update();
		}
	}
}


