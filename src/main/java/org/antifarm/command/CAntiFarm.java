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

package org.antifarm.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.antifarm.AntiFarm;

public class CAntiFarm implements CommandExecutor {
	public AntiFarm antifarm;

	public CAntiFarm(AntiFarm antifarm)
	{
		this.antifarm = antifarm;
	}

	public boolean onCommand(CommandSender sender, Command command,
							 String label, String[] args)
	{
		if (!sender.hasPermission("antifarm.antifarm"))
			return true;

		if (args.length < 1) {
			sender.sendMessage(command.getUsage());
			return true;
		}

		if (args[0].matches("r|rel|reload"))
			reload(sender);
		else
			sender.sendMessage(command.getUsage());

		return true;
	}

	private void reload(CommandSender sender)
	{
		if (!sender.hasPermission("antifarm.reload"))
			return;

		antifarm.onReload();
		sender.sendMessage("AntiFarm successfully reloaded.");
	}
}
