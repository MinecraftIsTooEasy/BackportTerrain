package org.moddedmite.bpt.world.biome;

import net.minecraft.BiomeGenBase;
import net.minecraft.Block;
import net.minecraft.Material;
import net.minecraft.World;
import net.xiaoyu233.fml.reload.utils.IdUtil;
import org.moddedmite.bpt.api.IBiome;

import java.util.Random;

public class BBiomes {
    protected static final Height height_Default = new Height(0.1F, 0.2F);
    protected static final Height height_ShallowWaters = new Height(-0.5F, 0.0F);
    protected static final Height height_Oceans = new Height(-1.0F, 0.1F);
    protected static final Height height_DeepOceans = new Height(-1.8F, 0.1F);
    protected static final Height height_LowPlains = new Height(0.125F, 0.05F);
    protected static final Height height_MidPlains = new Height(0.2F, 0.2F);
    protected static final Height height_LowHills = new Height(0.45F, 0.3F);
    protected static final Height height_HighPlateaus = new Height(1.5F, 0.025F);
    protected static final Height height_MidHills = new Height(1.0F, 0.5F);
    protected static final Height height_Shores = new Height(0.0F, 0.025F);
    protected static final Height height_RockyWaters = new Height(0.1F, 0.8F);
    protected static final Height height_LowIslands = new Height(0.2F, 0.3F);
    protected static final Height height_PartiallySubmerged = new Height(-0.2F, 0.1F);

    public static final BiomeGenBase ocean = (new BiomeOcean(IdUtil.getNextBiomeId())).color(112).name("Ocean").height(height_Oceans);
    public static final BiomeGenBase plains = (new BiomePlains(IdUtil.getNextBiomeId())).color(9286496).name("Plains");
    public static final BiomeGenBase desert = (new BiomeDesert(IdUtil.getNextBiomeId())).color(16421912).name("Desert").noRain().tempRain(2.0F, 0.0F).height(height_LowPlains);
    public static final BiomeGenBase extremeHills = (new BiomeHills(IdUtil.getNextBiomeId(), false)).color(6316128).name("Extreme Hills").height(height_MidHills).tempRain(0.2F, 0.3F);
    public static final BiomeGenBase forest = (new BiomeForest(IdUtil.getNextBiomeId(), 0)).color(353825).name("Forest");
    public static final BiomeGenBase taiga = (new BiomeTaiga(IdUtil.getNextBiomeId(), 0)).color(747097).name("Taiga").grassColor(5159473).tempRain(0.25F, 0.8F).height(height_MidPlains);
    public static final BiomeGenBase swampland = (new BiomeSwamp(IdUtil.getNextBiomeId())).color(522674).name("Swampland").grassColor(9154376).height(height_PartiallySubmerged).tempRain(0.8F, 0.9F);
    public static final BiomeGenBase river = (new BiomeRiver(IdUtil.getNextBiomeId())).color(255).name("River").height(height_ShallowWaters);
//    public static final BiomeGenBase hell = (new BiomeHell(IdUtil.getNextBiomeId())).color(16711680).name("Hell").noRain().tempRain(2.0F, 0.0F);
//    public static final BiomeGenBase sky = (new BiomeEnd(IdUtil.getNextBiomeId())).color(8421631).name("Sky").noRain();
    public static final BiomeGenBase frozenOcean = (new BiomeOcean(IdUtil.getNextBiomeId())).color(9474208).name("FrozenOcean").snow().height(height_Oceans).tempRain(0.0F, 0.5F);
    public static final BiomeGenBase frozenRiver = (new BiomeRiver(IdUtil.getNextBiomeId())).color(10526975).name("FrozenRiver").snow().height(height_ShallowWaters).tempRain(0.0F, 0.5F);
    public static final BiomeGenBase icePlains = (new BiomeSnow(IdUtil.getNextBiomeId(), false)).color(16777215).name("Ice Plains").snow().tempRain(0.0F, 0.5F).height(height_LowPlains);
    public static final BiomeGenBase iceMountains = (new BiomeSnow(IdUtil.getNextBiomeId(), false)).color(10526880).name("Ice Mountains").snow().height(height_LowHills).tempRain(0.0F, 0.5F);
    public static final BiomeGenBase mushroomIsland = (new BiomeMushroomIsland(IdUtil.getNextBiomeId())).color(16711935).name("MushroomIsland").tempRain(0.9F, 1.0F).height(height_LowIslands);
    public static final BiomeGenBase mushroomIslandShore = (new BiomeMushroomIsland(IdUtil.getNextBiomeId())).color(10486015).name("MushroomIslandShore").tempRain(0.9F, 1.0F).height(height_Shores);
    public static final BiomeGenBase beach = (new BiomeBeach(IdUtil.getNextBiomeId())).color(16440917).name("Beach").tempRain(0.8F, 0.4F).height(height_Shores);
    public static final BiomeGenBase desertHills = (new BiomeDesert(IdUtil.getNextBiomeId())).color(13786898).name("DesertHills").noRain().tempRain(2.0F, 0.0F).height(height_LowHills);
    public static final BiomeGenBase forestHills = (new BiomeForest(IdUtil.getNextBiomeId(), 0)).color(2250012).name("ForestHills").height(height_LowHills);
    public static final BiomeGenBase taigaHills = (new BiomeTaiga(IdUtil.getNextBiomeId(), 0)).color(1456435).name("TaigaHills").grassColor(5159473).tempRain(0.25F, 0.8F).height(height_LowHills);
    public static final BiomeGenBase extremeHillsEdge = (new BiomeHills(IdUtil.getNextBiomeId(), true)).color(7501978).name("Extreme Hills Edge").height(height_MidHills.attenuate()).tempRain(0.2F, 0.3F);
    public static final BiomeGenBase jungle = (new BiomeJungle(IdUtil.getNextBiomeId(), false)).color(5470985).name("Jungle").grassColor(5470985).tempRain(0.95F, 0.9F);
    public static final BiomeGenBase jungleHills = (new BiomeJungle(IdUtil.getNextBiomeId(), false)).color(2900485).name("JungleHills").grassColor(5470985).tempRain(0.95F, 0.9F).height(height_LowHills);
    public static final BiomeGenBase jungleEdge = (new BiomeJungle(IdUtil.getNextBiomeId(), true)).color(6458135).name("JungleEdge").grassColor(5470985).tempRain(0.95F, 0.8F);
    public static final BiomeGenBase deepOcean = (new BiomeOcean(IdUtil.getNextBiomeId())).color(48).name("Deep Ocean").height(height_DeepOceans);
    public static final BiomeGenBase stoneBeach = (new BiomeStoneBeach(IdUtil.getNextBiomeId())).color(10658436).name("Stone Beach").tempRain(0.2F, 0.3F).height(height_RockyWaters);
    public static final BiomeGenBase coldBeach = (new BiomeBeach(IdUtil.getNextBiomeId())).color(16445632).name("Cold Beach").tempRain(0.05F, 0.3F).height(height_Shores).snow();
    public static final BiomeGenBase birchForest = (new BiomeForest(IdUtil.getNextBiomeId(), 2)).name("Birch Forest").color(3175492);
    public static final BiomeGenBase birchForestHills = (new BiomeForest(IdUtil.getNextBiomeId(), 2)).name("Birch Forest Hills").color(2055986).height(height_LowHills);
    public static final BiomeGenBase roofedForest = (new BiomeForest(IdUtil.getNextBiomeId(), 3)).color(4215066).name("Roofed Forest");
    public static final BiomeGenBase coldTaiga = (new BiomeTaiga(IdUtil.getNextBiomeId(), 0)).color(3233098).name("Cold Taiga").grassColor(5159473).snow().tempRain(-0.5F, 0.4F).height(height_MidPlains).waterColor(16777215);
    public static final BiomeGenBase coldTaigaHills = (new BiomeTaiga(IdUtil.getNextBiomeId(), 0)).color(2375478).name("Cold Taiga Hills").grassColor(5159473).snow().tempRain(-0.5F, 0.4F).height(height_LowHills).waterColor(16777215);
    public static final BiomeGenBase megaTaiga = (new BiomeTaiga(IdUtil.getNextBiomeId(), 1)).color(5858897).name("Mega Taiga").grassColor(5159473).tempRain(0.3F, 0.8F).height(height_MidPlains);
    public static final BiomeGenBase megaTaigaHills = (new BiomeTaiga(IdUtil.getNextBiomeId(), 1)).color(4542270).name("Mega Taiga Hills").grassColor(5159473).tempRain(0.3F, 0.8F).height(height_LowHills);
    public static final BiomeGenBase extremeHillsPlus = (new BiomeHills(IdUtil.getNextBiomeId(), true)).color(5271632).name("Extreme Hills+").height(height_MidHills).tempRain(0.2F, 0.3F);
    public static final BiomeGenBase savanna = (new BiomeSavanna(IdUtil.getNextBiomeId())).color(12431967).name("Savanna").tempRain(1.2F, 0.0F).noRain().height(height_LowPlains);
    public static final BiomeGenBase savannaPlateau = (new BiomeSavanna(IdUtil.getNextBiomeId())).color(10984804).name("Savanna Plateau").tempRain(1.0F, 0.0F).noRain().height(height_HighPlateaus);
    public static final BiomeGenBase mesa = (new BiomeMesa(IdUtil.getNextBiomeId(), false, false)).color(14238997).name("Mesa");
    public static final BiomeGenBase mesaPlateau_F = (new BiomeMesa(IdUtil.getNextBiomeId(), false, true)).color(11573093).name("Mesa Plateau F").height(height_HighPlateaus);
    public static final BiomeGenBase mesaPlateau = (new BiomeMesa(IdUtil.getNextBiomeId(), false, false)).color(13274213).name("Mesa Plateau").height(height_HighPlateaus);

    private BBiomes() {
    }

    public static class BBiome extends BiomeGenBase implements IBiome {
        public Block topBlock;
        public Block fillerBlock;
        public int field_150604_aj;
        
        public BBiome(int id) {
            super(id);
            this.topBlock = Block.grass;
            this.fillerBlock = Block.dirt;
            this.field_150604_aj = 0;
        }

        public BBiome name(String n) {
            this.biomeName = n;
            return this;
        }

        public BBiome color(int c) {
            this.color = c;
            return this;
        }

        public BBiome tempRain(float temperature, float rainfall) {
            if (temperature > 0.1F && temperature < 0.2F) {
                throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
            }
            this.temperature = temperature;
            this.rainfall = rainfall;
            return this;
        }

        public BBiome height(Height h) {
            this.minHeight = h.rootHeight;
            this.maxHeight = h.variation;
            return this;
        }

        public BBiome surface(Block top, Block filler) {
            this.topBlock = top;
            this.fillerBlock = filler;
            super.topBlock = (byte) top.blockID;
            super.fillerBlock = (byte) filler.blockID;
            return this;
        }

        public BBiome snow() {
            this.setEnableSnow();
            return this;
        }

        public BBiome noRain() {
            this.enableRain = false;
            return this;
        }

        public BBiome grassColor(int c) {
            this.field_76754_C = c;
            return this;
        }

        public BBiome waterColor(int c) {
            this.waterColorMultiplier = c;
            return this;
        }
        
        public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
            this.genBiomeTerrain(p_150573_1_, p_150573_2_, p_150573_3_, p_150573_4_, p_150573_5_, p_150573_6_, p_150573_7_);
        }
        
        public final void genBiomeTerrain(World p_150560_1_, Random p_150560_2_, Block[] p_150560_3_, byte[] p_150560_4_, int p_150560_5_, int p_150560_6_, double p_150560_7_) {
            boolean flag = true;
            Block block = this.topBlock;
            byte b0 = (byte) (this.field_150604_aj & 255);
            Block block1 = this.fillerBlock;
            int k = -1;
            int l = (int) (p_150560_7_ / 3.0D + 3.0D + p_150560_2_.nextDouble() * 0.25D);
            int i1 = p_150560_5_ & 15;
            int j1 = p_150560_6_ & 15;
            int k1 = p_150560_3_.length / 256;
            
            for (int l1 = 255; l1 >= 0; --l1) {
                int i2 = (j1 * 16 + i1) * k1 + l1;
                
                if (l1 <= 0 + p_150560_2_.nextInt(5)) {
                    p_150560_3_[i2] = Block.bedrock;
                } else {
                    Block block2 = p_150560_3_[i2];
                    
                    if (block2 != null && block2.blockMaterial != Material.air) {
                        if (block2 == Block.stone) {
                            if (k == -1) {
                                if (l <= 0) {
                                    block = null;
                                    b0 = 0;
                                    block1 = Block.stone;
                                } else if (l1 >= 59 && l1 <= 64) {
                                    block = this.topBlock;
                                    b0 = (byte) (this.field_150604_aj & 255);
                                    block1 = this.fillerBlock;
                                }
                                
                                if (l1 < 63 && (block == null || block.blockMaterial == Material.air)) {
                                    if (this.getFloatTemperature(/*p_150560_5_, l1, p_150560_6_*/) < 0.15F) {
                                        block = Block.ice;
                                        b0 = 0;
                                    } else {
                                        block = Block.waterStill;
                                        b0 = 0;
                                    }
                                }
                                
                                k = l;
                                
                                if (l1 >= 62) {
                                    p_150560_3_[i2] = block;
                                    p_150560_4_[i2] = b0;
                                } else if (l1 < 56 - l) {
                                    block = null;
                                    block1 = Block.stone;
                                    p_150560_3_[i2] = Block.gravel;
                                } else {
                                    p_150560_3_[i2] = block1;
                                }
                            } else if (k > 0) {
                                --k;
                                p_150560_3_[i2] = block1;
                                
                                if (k == 0 && block1 == Block.sand) {
                                    k = p_150560_2_.nextInt(4) + Math.max(0, l1 - 63);
                                    block1 = Block.sandStone;
                                }
                            }
                        }
                    } else {
                        k = -1;
                    }
                }
            }
        }
    }

    public static class Height {
        public float rootHeight;
        public float variation;

        public Height(float rootHeight, float variation) {
            this.rootHeight = rootHeight;
            this.variation = variation;
        }

        /**
         * Reduces the baseHeight by 20%, and the variation intensity by 40%, and returns the resulting Height
         * object
         */
        public Height attenuate() {
            return new Height(this.rootHeight * 0.8F, this.variation * 0.6F);
        }
    }
}
