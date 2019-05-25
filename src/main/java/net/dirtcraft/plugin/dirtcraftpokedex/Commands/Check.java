package net.dirtcraft.plugin.dirtcraftpokedex.Commands;

import com.pixelmonmod.pixelmon.Pixelmon;
import com.pixelmonmod.pixelmon.enums.EnumSpecies;
import net.dirtcraft.plugin.dirtcraftpokedex.DirtCraftPokedex;
import net.dirtcraft.plugin.dirtcraftpokedex.Utility.CheckDex;
import net.minecraft.entity.player.EntityPlayerMP;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.pagination.PaginationList;

public class Check implements CommandExecutor {

    private final DirtCraftPokedex main;

    public Check(DirtCraftPokedex main) {
        this.main = main;
    }

    @Override
    public CommandResult execute(CommandSource source, CommandContext arguments) throws CommandException {
        if (source instanceof Player) {
            Player player = (Player) source;
            EntityPlayerMP entity = (EntityPlayerMP) source;

            CheckDex checkDex = new CheckDex();

            int caught = Pixelmon.storageManager.getParty(entity).pokedex.countCaught();
            double percent = Double.valueOf(main.decimalFormat.format((double) caught / ((double) EnumSpecies.values().length - 2.0D) * 100.0D));

            PaginationList.Builder pagination = PaginationList.builder();

            pagination.title(main.format("&cDirtCraft &bPokédex"));
            pagination.padding(main.format("&7&m-"));

            if (percent == 100 && !player.hasPermission("group.pokemaster")) {

                checkDex.onCheck("Pokémaster", percent, player, entity, pagination);

            } else if (percent < 100 && percent >= 70 && !player.hasPermission("group.ace")) {

                checkDex.onCheck("Ace", percent, player, entity, pagination);

            } else if (percent < 70 && percent >= 50 && !player.hasPermission("group.expert")) {

                checkDex.onCheck("Expert", percent, player, entity, pagination);

            } else if (percent < 50 && percent >= 30 && !player.hasPermission("group.knowledgable")) {

                checkDex.onCheck("Knowledgable", percent, player, entity, pagination);

            } else if (percent < 30 && percent >= 10 && !player.hasPermission("group.intermedius")) {

                checkDex.onCheck("Intermedius", percent, player, entity, pagination);

            } else {

                checkDex.onCheck("Rookie", percent, player, entity, pagination);

            }


        } else {
            throw new CommandException(main.format("&cOnly a player can check their Pokédex"));
        }

        return CommandResult.success();
    }

}