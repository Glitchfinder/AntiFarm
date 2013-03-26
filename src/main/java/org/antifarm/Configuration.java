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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

public class Configuration extends YamlConfiguration {
	public File file;
	public HashMap<Integer, Range> rangesById;
	public HashMap<EntityType, ArrayList<Range>> rangesByType;

	public Configuration(File file) {
		this.file         = file;
		this.rangesById   = new HashMap<Integer, Range>();
		this.rangesByType = new HashMap<EntityType, ArrayList<Range>>();
	}

	public void load() {
		rangesById.clear();
		rangesByType.clear();

		try {
			super.load(file);
		} catch (Exception e) {
			// Log configuration load error
			return;
		}

		for (Map<?, ?> map : getMapList("ranges")) {
			Range rng = new Range();
			List<String> strl;

			try {
				rng.radius   = getMapInt(map, "radius");
				rng.cooldown = getMapInt(map, "cooldown");
				rng.mobCount = getMapInt(map, "mobCount");
				strl         = getMapList(map, "mobs");
			} catch (Exception e) {
				continue;
			}

			for (String str : strl) {
				EntityType type = EntityType.valueOf(EntityType.class, str);

				if (type == null)
					continue;

				rng.types.add(type);
				ArrayList<Range> elist = rangesByType.get(type);

				if (elist == null) {
					elist = new ArrayList<Range>();
					elist.add(rng);
					rangesByType.put(type, elist);
				} else {
					elist.add(rng);
				}
			}

			rangesById.put(rng.id, rng);
		}
	}

	private int getMapInt(Map<?, ?> map, String key)
		throws Exception
	{
		Object obj = map.get(key);

		if (!(obj instanceof Integer))
			throw new Exception();

		return (Integer) obj;
	}

	private List<String> getMapList(Map<?, ?> map, String key)
		throws Exception
	{
		Object obj = map.get(key);

		if (!(obj instanceof List))
			throw new Exception();

		return (List<String>) obj;
	}
}
