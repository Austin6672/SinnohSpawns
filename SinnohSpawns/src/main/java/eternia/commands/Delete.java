package eternia.commands;

import eternia.configuration.ConfigManager;
import eternia.utilities.Permissions;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class Delete implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        String zone = args.<String>getOne("zone").get().toLowerCase();

        if(!ConfigManager.getZonesNode(zone).isVirtual()) {

            ConfigManager.getZonesNode(zone).setValue(null);
            ConfigManager.save();
            src.sendMessage(Text.of(TextColors.GREEN, zone + " has been deleted"));

        } else {
            src.sendMessage(Text.of(TextColors.RED, "That zone does not exist"));
        }

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission(Permissions.DELETE)
                .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("zone"))))
                .executor(new Delete())
                .build();
    }
}
