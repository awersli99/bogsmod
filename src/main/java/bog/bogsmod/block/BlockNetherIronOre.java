package bog.bogsmod.block;

import bog.bogsmod.BogsMod;
import net.minecraft.src.Block;
import net.minecraft.src.Material;
import net.minecraft.src.World;

import java.util.Random;

public class BlockNetherIronOre extends Block {

    private static final Random explosionRNG = new Random();

    public BlockNetherIronOre(int i) {
        super(i, Material.rock);
    }

    public void onBlockRemoval(World world, int x, int y, int z) {
        // Explodes with 15% probability
        float explosionSize = 2.0f;
        int explosionPercentage = 15;
        if (BogsMod.probability(explosionRNG, explosionPercentage))
            world.createExplosion(null, x, y, z, explosionSize);
    }
}
