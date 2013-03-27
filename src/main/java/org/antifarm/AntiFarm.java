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
	import java.io.File;
//* IMPORTS: BUKKIT
	import org.bukkit.plugin.java.JavaPlugin;
//* IMPORTS: SPOUT
	//* NOT NEEDED
//* IMPORTS: OTHER
	import org.antifarm.command.CAntiFarm;

public class AntiFarm extends JavaPlugin
{
	public Configuration config;

	public void onLoad() {
		config = new Configuration(new File(getDataFolder(), "config.yml"));
	}

	public void onEnable() {
		onReload();
		getCommand("antifarm").setExecutor(new CAntiFarm(this));
	}

	public void onDisable() {}

	public void onReload() {
		if (!config.file.exists())
			saveResource("config.yml", false);

		config.load();
	}
}
