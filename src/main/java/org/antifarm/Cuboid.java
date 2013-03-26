/*
 * Copyright (c) 2012-2013 Sean Porter <glitchkey@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.antifarm;

//* IMPORTS: JDK/JRE
	//* NOT NEEDED
//* IMPORTS: BUKKIT
	import org.bukkit.block.Block;
	import org.bukkit.Location;
	import org.bukkit.World;
//* IMPORTS: OTHER
	//* NOT NEEDED

public class Cuboid {
	private double[] faces = new double[6];
	private World world;

	public Cuboid(Location corner1, Location corner2) {
		this.setWorld(corner1.getWorld());

		for (short i = 0; i < 3; i++) {
			double f1 = 0;
			double f2 = 0;

			switch (i) {
				case 0:
					f1 = corner1.getBlockX();
					f2 = corner2.getBlockX();
					break;
				case 1:
					f1 = corner1.getBlockY();
					f2 = corner2.getBlockY();
					break;
				default:
					f1 = corner1.getBlockZ();
					f2 = corner2.getBlockZ();
					break;
			}

			this.setFace((i * 2), ((f1 < f2) ? f1 : f2));
			this.setFace(((i * 2) + 1), ((f1 < f2) ? f2 : f1));
		}
	}

	public Cuboid(Block corner1, Block corner2) {
		this(corner1.getLocation(), corner2.getLocation());
	}

	public Cuboid(
		World world,
		double x1,
		double y1,
		double z1,
		double x2,
		double y2,
		double z2
	) {
		this.setWorld(world);

		for (short i = 0; i < 3; i++) {
			double f1 = 0;
			double f2 = 0;

			switch (i) {
				case 0:
					f1 = x1;
					f2 = x2;
					break;
				case 1:
					f1 = y1;
					f2 = y2;
					break;
				default:
					f1 = z1;
					f2 = z2;
					break;
			}

			this.setFace((i * 2), ((f1 < f2) ? f1 : f2));
			this.setFace(((i * 2) + 1), ((f1 < f2) ? f2 : f1));
		}
	}

	public Cuboid(
		World world,
		int x1,
		int y1,
		int z1,
		int x2,
		int y2,
		int z2
	) {
		this (
			world,
			(double) x1,
			(double) y1,
			(double) z1,
			(double) x2,
			(double) y2,
			(double) z2
		);
	}

	public Cuboid(Cuboid cuboid) {
		this.setWorld(cuboid.getWorld());

		for (int i = 0; i < 6; i++) {
			this.setFace(i, cuboid.getFace(i));
		}
	}

	public World getWorld() {
		return this.world;
	}

	public Cuboid setWorld(World world)  {
		this.world = world;
		return this;
	}

	public double getFace(Faces face) {
		return this.getFace(face.getId());
	}

	public double getFace(int face) {
		if (face < 0 || face >= 6)
			return 0;

		return this.faces[face];
	}

	public double getFace(short face) {
		return this.getFace((int) face);
	}

	public double getFace(byte face) {
		return this.getFace((int) face);
	}

	public Cuboid setFace(Faces face, double value) {
		return this.setFace(face.getId(), value);
	}

	public Cuboid setFace(Faces face, int value) {
		return this.setFace(face.getId(), (double) value);
	}

	public Cuboid setFace(int face, double value) {
		if (face < 0 || face >= 6)
			return this;

		this.faces[face] = value;
		return this;
	}

	public Cuboid setFace(int face, int value) {
		return this.setFace(face, (double) value);
	}

	public Cuboid setFace(short face, double value) {
		return this.setFace((int) face, value);
	}

	public Cuboid setFace(short face, int value) {
		return this.setFace((int) face, (double) value);
	}

	public Cuboid setFace(byte face, double value) {
		return this.setFace((int) face, value);
	}

	public Cuboid setFace(byte face, int value) {
		return this.setFace((int) face, (double) value);
	}

	public Cuboid expand(Location location) {
		if (location.getWorld() != this.getWorld())
			return this;

		if (this.withinBounds(location))
			return this;

		for (int i = 0; i < 3; i++) {
			double newValue = 0;

			switch (i) {
				case 0:
					newValue = location.getBlockX();
					break;
				case 1:
					newValue = location.getBlockY();
					break;
				default:
					newValue = location.getBlockZ();
					break;
			}

			if (newValue < this.getFace(i * 2))
				this.setFace((i * 2), newValue);
			else if (newValue > this.getFace((i * 2) + 1))
				this.setFace(((i * 2) + 1), newValue);
		}

		return this;
	}

	public Cuboid expand(Block block) {
		return this.expand(block.getLocation());
	}

	public Cuboid expand(double x, double y, double z) {
		return this.expand(new Location(this.getWorld(), x, y, z));
	}

	public Cuboid expand(int x, int y, int z) {
		return this.expand(new Location(
			this.getWorld(),
			(double) x,
			(double) y,
			(double) z)
		);
	}

	public Cuboid expand(Cuboid cuboid) {
		if (cuboid.getWorld() != this.getWorld())
			return this;

		for (int i = 0; i < 6; i++) {
			double newValue = cuboid.getFace(i);

			if ((i % 2) == 0 && this.getFace(i) < newValue)
				this.setFace(i, newValue);
			else if ((i % 2) == 1 && this.getFace(i) > newValue)
				this.setFace(i, newValue);
		}

		return this;
	}

	public boolean withinBounds(Location location) {
		if (this.getWorld() != location.getWorld())
			return false;
		else if (this.getFace(Faces.WEST) > location.getX())
			return false;
		else if (this.getFace(Faces.EAST) < location.getX())
			return false;
		else if (this.getFace(Faces.BOTTOM) > location.getY())
			return false;
		else if (this.getFace(Faces.TOP) < location.getY())
			return false;
		else if (this.getFace(Faces.NORTH) > location.getZ())
			return false;
		else if (this.getFace(Faces.SOUTH)  < location.getZ())
			return false;

		return true;
	}

	public boolean withinBounds(Block block) {
		return this.withinBounds(block.getLocation());
	}

	public boolean withinBounds(World world, double x, double y, double z) {
		return this.withinBounds(new Location(world, x, y, z));
	}

	public boolean withinBounds(World world, int x, int y, int z) {
		return this.withinBounds(new Location(
			world,
			(double) x,
			(double) y,
			(double) z)
		);
	}
	
	public boolean withinBounds(Cuboid cuboid) {
		if (cuboid.getWorld() != this.getWorld())
			return false;

		for (int i = 0; i < 6; i++) {
			double f = cuboid.getFace(i);
			double f1 = this.getFace(i + 1);
			double f2 = this.getFace(i);

			if((i % 2) == 1) {
				f1 = f2;
				f2 = this.getFace(i - 1);
			}

			if (f1 >= f && f2 <= f)
				return true;
		}

		return false;
	}
	
	public boolean withinBounds(double x, double y, double z) {
		Location loc = new Location(this.getWorld(), x, y, z);
		return this.withinBounds(loc);
	}

	public boolean withinBounds(int x, int y, int z) {
		Location loc = new Location(
			this.getWorld(),
			(double) x,
			(double) y,
			(double) z
		);

		return this.withinBounds(loc);
	}
}
