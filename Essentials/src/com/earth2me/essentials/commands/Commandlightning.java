package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n._;
import com.earth2me.essentials.User;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Commandlightning extends EssentialsCommand
{
	public Commandlightning()
	{
		super("lightning");
	}

	@Override
	public void run(final Server server, final CommandSender sender, final String commandLabel, final String[] args) throws Exception
	{

		User user = null;
		if (sender instanceof Player)
		{
			user = ess.getUser(((Player)sender));
		}
		if (args.length < 1 & user != null)
		{
			user.getWorld().strikeLightning(user.getTargetBlock(null, 600).getLocation());
			return;
		}

		if (server.matchPlayer(args[0]).isEmpty())
		{
			throw new Exception(_("playerNotFound"));
		}

		for (Player matchPlayer : server.matchPlayer(args[0]))
		{
			sender.sendMessage(_("lightningUse", matchPlayer.getDisplayName()));
			matchPlayer.getWorld().strikeLightning(matchPlayer.getLocation());
			if (!ess.getUser(matchPlayer).isGodModeEnabled())
			{
				matchPlayer.setHealth(matchPlayer.getHealth() < 5 ? 0 : matchPlayer.getHealth() - 5);
			}
			if (ess.getSettings().warnOnSmite())
			{
				matchPlayer.sendMessage(_("lightningSmited"));
			}
		}
	}
}
