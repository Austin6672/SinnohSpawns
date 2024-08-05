package eternia.commands;

import eternia.utilities.Permissions;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.service.pagination.PaginationList;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.List;
import java.util.stream.Collectors;

import static eternia.configuration.ConfigManager.getZoneRoot;

public class ListRoutes implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) {

        List<Text> contents = getZoneRoot().getChildrenMap().entrySet().stream()
                .map(map -> Text.of(TextColors.AQUA, map.getKey().toString()))
                .collect(Collectors.toList());

        PaginationList.builder()
                .title(Text.of(TextColors.AQUA, "Zones"))
                .padding(Text.of(TextColors.YELLOW, "="))
                .contents(contents)
                .sendTo(src);

        return CommandResult.success();
    }

    public static CommandSpec build() {
        return CommandSpec.builder()
                .permission(Permissions.LIST)
                .executor(new ListRoutes())
                .build();

    }

}
