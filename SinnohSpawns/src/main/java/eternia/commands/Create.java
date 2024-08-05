package eternia.commands;

import eternia.utilities.Permissions;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import eternia.configuration.ConfigManager;

public class Create implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        if(src instanceof Player) {
            String zone = args.<String>getOne("name").get().toLowerCase();
            if(ConfigManager.getZonesNode(zone).isVirtual()) {

                int radius = args.<Integer>getOne("radius").get();
                Player player = (Player) src;
                int xLoc = player.getLocation().getBlockX();
                int yLoc = player.getLocation().getBlockY();
                int zLoc = player.getLocation().getBlockZ();

                ConfigManager.getZonesNode(zone, "Pokemon", "RegBidoof").setValue(500);
                ConfigManager.getZonesNode(zone, "Zone", "Radius").setValue(radius);
                ConfigManager.getZonesNode(zone, "Zone", "X").setValue(xLoc);
                ConfigManager.getZonesNode(zone, "Zone", "Y").setValue(yLoc);
                ConfigManager.getZonesNode(zone, "Zone", "Z").setValue(zLoc);
                ConfigManager.getZonesNode(zone, "Zone", "Max-Spawns").setValue(5);
                ConfigManager.getZonesNode(zone, "Zone", "SpawnPos").setValue("Normal");

                src.sendMessage(Text.of(TextColors.GREEN, "Route has been created!"));
                ConfigManager.save();

            } else {
                src.sendMessage(Text.of(TextColors.RED, "That zone already exists"));
            }

        } else {
            src.sendMessage(Text.of(TextColors.RED, "Only a player can enter this command"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission(Permissions.CREATE)
                .arguments(GenericArguments.seq(
                        GenericArguments.string(Text.of("name")),
                        GenericArguments.integer(Text.of("radius"))))
                .executor(new Create())
                .build();
    }
}
