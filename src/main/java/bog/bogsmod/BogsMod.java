package bog.bogsmod;

import bog.bogsmod.block.BlockNetherIronOre;
import bog.bogsmod.entity.EntitySteelBoat;
import bog.bogsmod.item.ItemSteelBoat;
import bog.bogsmod.render.RenderSteelBoat;
import net.fabricmc.api.ModInitializer;
import net.minecraft.src.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.BlockHelper;
import turniplabs.halplibe.helper.ItemHelper;
import turniplabs.halplibe.helper.RecipeHelper;
import turniplabs.halplibe.helper.EntityHelper;

import java.util.Random;

public class BogsMod implements ModInitializer {
    public static final String MOD_ID = "bogsmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static String modName(String name) {
        return BogsMod.MOD_ID + "." + name;
    }

    static int bogsModIds = 700;

    public static boolean probability(Random rand, double percent) {
        return percent > 0 && rand.nextInt(100) <= percent;
    }

    // - { CREATE NEW BLOCKS } -
    public static final Block flintBlock = BlockHelper.createBlock(MOD_ID, new Block(bogsModIds+1, Material.rock), "FlintBlock", "FlintBlock.png", Block.soundStoneFootstep, 3f, 2f, 0f);
    public static final Block netherIronOre = BlockHelper.createBlock(MOD_ID, new BlockNetherIronOre(bogsModIds+2), "NetherIronOre", "NetherIronOre.png", Block.soundStoneFootstep, 4f, 6f, 0f);

    // - { CREATE NEW ITEMS } -
    public static final Item steelBoat = ItemHelper.createItem(MOD_ID, new ItemSteelBoat(bogsModIds+3), "SteelBoat", "SteelBoat.png");

    // - { ORE GENERATORS } -
    public static final WorldGenerator netherIronOreGen = new WorldGenMinable(netherIronOre.blockID, 10, false);

    @Override
    public void onInitialize() {
        // - { REGISTER NEW CRAFTING RECIPES } -
        RecipeHelper.Crafting.createRecipe(flintBlock, 1, new Object[]{"FFF", "F F", "FFF", 'F', Item.flint}); // flint to flint block
        RecipeHelper.Crafting.createShapelessRecipe(Item.flint, 8, new Object[]{new ItemStack(flintBlock, 1)}); // flint block to flint

        RecipeHelper.Crafting.createRecipe(steelBoat, 1, new Object[]{"F F", "FFF", 'F', Item.ingotSteel});

        // - { REGISTER SMELTING RECIPES } -
        RecipeHelper.smeltingManager.addSmelting(BogsMod.netherIronOre.blockID, new ItemStack(Item.ingotIron, 1));
        RecipeHelper.blastingManager.addSmelting(BogsMod.netherIronOre.blockID, new ItemStack(Item.ingotIron, 1));

        EntityHelper.createEntity(EntitySteelBoat.class, new RenderSteelBoat(), 704, "SteelBoat");

        LOGGER.info("BogsMod initialized.");
    }
}
