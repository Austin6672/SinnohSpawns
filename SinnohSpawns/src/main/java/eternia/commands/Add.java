package eternia.commands;

import eternia.configuration.ConfigManager;
import eternia.utilities.Permissions;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Add implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        String zone = args.<String>getOne(Text.of("zone")).get();
        String alias = args.<String>getOne(Text.of("alias")).get();
        int rarity = args.<Integer>getOne(Text.of("rarity")).get();

        if(!ConfigManager.getZonesNode(zone).isVirtual()) {
            if(!ConfigManager.getPokemonNode(alias).isVirtual()) {
                ConfigManager.getZonesNode(zone, "Pokemon", alias).setValue(rarity);
                ConfigManager.save();
                src.sendMessage(Text.of(TextColors.GREEN, zone + " now has " + alias + " as an alias"));
            } else {
                src.sendMessage(Text.of(TextColors.RED, alias + " is not a valid alias"));
            }
        } else {
            src.sendMessage(Text.of(TextColors.RED, zone + " is not a valid zone!"));
        }

        return CommandResult.success();
    }

    static CommandSpec build() {
        return CommandSpec.builder()
                .permission(Permissions.ADD)
                .arguments(GenericArguments.seq(
                        GenericArguments.string(Text.of("zone")),
                        GenericArguments.string(Text.of("alias")),
                        GenericArguments.integer(Text.of("rarity"))))
                .executor(new Add())
                .build();
    }
}