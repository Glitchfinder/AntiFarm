/*
 * Copyright 2013 James Geboski <jgeboski@gmail.com>
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

import java.util.ArrayList;

import org.bukkit.entity.EntityType;

public class Range {
	private static int nextId = 0;

	public int id;
	public int radius;
	public int cooldown;
	public int mobCount;

	public ArrayList<EntityType> types;

	public Range() {
		this.id    = nextId;
		this.types = new ArrayList<EntityType>();

		nextId++;
	}
}
