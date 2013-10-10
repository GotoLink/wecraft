package wecraft;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid="wecraft",name="W3(r4ft",version="0.1")
@NetworkMod(clientSideRequired=true)
public class Wecraft
{
	//public static final Block saverItem;
    public static Block conveyorBelt,sensorArrow,drill,sensorFire,gitter,drillHead;
    public static Block sensorTime,grill,waterMaker;
    public static Item metalStick, drillHeadItem;
    public static int waterTankRadiusWide, waterTankRadiusDepth;
    public static int convertyTexture;
	@EventHandler
    public void preload(FMLPreInitializationEvent event)
    {
		CreativeTabs tab = new CreativeTabs("W3(r4ft");
    	Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        cfg.load();
       // saverItem = new BlockWtSaverItem(cfg.get("ItemSaverID", 3170), 2).setHardness(0.5F).setResistance(1.0F).setBlockName("Item Saver").setStepSound(Block.soundSandFootstep);
        conveyorBelt = new BlockConveyorBelt(cfg.getBlock("ConveyorBeltID", 3169).getInt()).setHardness(0.5F).setResistance(3.0F).setTextureName("wecraft:ConvertyBelt/ConveyorBelt_").setUnlocalizedName("Conveyor Belt").setStepSound(Block.soundMetalFootstep).setCreativeTab(tab);
        sensorArrow = new BlockSensorArrow(cfg.getBlock("ArrowSensorID", 3161).getInt()).setHardness(0.3F).setResistance(2.0F).setTextureName("wecraft:SensorArrow").setUnlocalizedName("Arrow Sensor").setStepSound(Block.soundClothFootstep).setCreativeTab(tab);
        drill = new BlockDrill(cfg.getBlock("DrillID", 3166).getInt()).setHardness(1.0F).setResistance(2000.0F).setTextureName("wecraft:Drill").setUnlocalizedName("Drill").setStepSound(Block.soundWoodFootstep).setCreativeTab(tab);
        sensorFire = new BlockSensorFire(cfg.getBlock("FireSensorID", 3164).getInt()).setHardness(4.0F).setResistance(7.0F).setTextureName("wecraft:SensorFire").setUnlocalizedName("Fire Sensor").setStepSound(Block.soundStoneFootstep).setCreativeTab(tab);
        gitter = new BlockGitter(cfg.getBlock("MetalMeshID", 3168).getInt()).setHardness(0.4F).setResistance(1.0F).setTextureName("wecraft:gitter").setUnlocalizedName("Metal Mesh").setStepSound(Block.soundMetalFootstep).setCreativeTab(tab);
        drillHead = new BlockDrillHead(cfg.getBlock("DrillHeadID", 3167).getInt()).setHardness(9.0F).setResistance(9.0F).setTextureName("iron_block").setUnlocalizedName("Drill-Head").setStepSound(Block.soundStoneFootstep);
        sensorTime = new BlockSensorTime(cfg.getBlock("TimeSensorID",3163).getInt()).setHardness(2.0F).setResistance(5.0F).setTextureName("wecraft:Sensor").setUnlocalizedName("Time Sensor").setStepSound(Block.soundWoodFootstep).setCreativeTab(tab);
        grill = new BlockGrill(cfg.getBlock("BurnerID", 3165).getInt()).setHardness(1.0F).setResistance(5.0F).setTextureName("wecraft:grill").setUnlocalizedName("Burner").setStepSound(Block.soundMetalFootstep).setCreativeTab(tab);
        waterMaker = new BlockWaterMaker(cfg.getBlock("WaterTankID", 3162).getInt()).setHardness(3.0F).setResistance(7.0F).setTextureName("wecraft:WaterMaker").setUnlocalizedName("Water Tank").setStepSound(Block.soundStoneFootstep).setCreativeTab(tab);
        drillHeadItem = new Item(cfg.getItem("DrillHeadItemID", 4100).getInt()).setTextureName("wecraft:DrillHead").setUnlocalizedName("Drill Head").setMaxStackSize(1).setCreativeTab(tab);
        metalStick = new Item(cfg.getItem("MetalStickID", 4101).getInt()).setTextureName("wecraft:MetalStick").setUnlocalizedName("Metal Stick").setMaxStackSize(64).setCreativeTab(tab);
        waterTankRadiusWide = cfg.get("general", "WaterTankRadiusWide", 5).getInt();
        waterTankRadiusDepth = cfg.get("general", "WaterTankRadiusDepth", 5).getInt();
        convertyTexture = cfg.get("general","ConvertyBeltAnimation", 1).getInt();
        cfg.save();
    }
	@EventHandler
	public void load(FMLInitializationEvent event)
	{
      /*  //Itemsaver
       GameRegistry.registerBlock(saverItem, "ItemSaver");
       LanguageRegistry.addName(saverItem,  "Item Saver");
       GameRegistry.addRecipe(new ItemStack(saverItem, 1), new Object[] {
           "XXX" , "X S", "XXX" , Character.valueOf('X'), Block.planks, Character.valueOf('S'), mod_Wecraft.gitter
       }); */
        
       //ConvertyBelt
       GameRegistry.registerBlock(conveyorBelt, "ConveyorBelt");
       LanguageRegistry.addName(conveyorBelt,  "Conveyor Belt");
       GameRegistry.addRecipe(new ItemStack(conveyorBelt, 6),
           "XXX" , "S#S", "XXX" , 
           Character.valueOf('X'), Wecraft.metalStick,
           Character.valueOf('#'), Item.redstone,
           Character.valueOf('S'), Block.cobblestone);
       
       //Arrow Sensor       
       GameRegistry.registerBlock(sensorArrow, "ArrowTarget");
       LanguageRegistry.addName(sensorArrow,  "Arrow Target");
       GameRegistry.addRecipe(new ItemStack(sensorArrow),
           "XXX" , "S#S" , "XXX" ,
           Character.valueOf('X'), Block.cloth,
           Character.valueOf('S'), Wecraft.gitter,
           Character.valueOf('#'), Item.arrow);
       
       //Drill         
       GameRegistry.registerBlock(drill, "Drill");
       LanguageRegistry.addName(drill,  "Drill");
       GameRegistry.addRecipe(new ItemStack(drill),
           "X X" , "S#S" , "XDX" ,
           Character.valueOf('X'), Block.planks,
           Character.valueOf('S'), Item.redstone,
           Character.valueOf('#'), Item.diamond,
           Character.valueOf('D'), Wecraft.drillHeadItem);
       
       //Fire Sensor
       GameRegistry.registerBlock(sensorFire, "FireAlarm");
       LanguageRegistry.addName(sensorFire,  "Fire Alarms");
       GameRegistry.addRecipe(new ItemStack(sensorFire),
          "SSS" , "SNS" , "X#X" ,
          Character.valueOf('X'), Wecraft.metalStick,
          Character.valueOf('#'), Item.redstone,
          Character.valueOf('N'), Block.netherrack,
          Character.valueOf('S'), Block.stone);
       GameRegistry.addRecipe(new ItemStack(sensorFire),
          "SSS" , "SNS" , "X#X" ,
          Character.valueOf('X'), Wecraft.metalStick,
          Character.valueOf('#'), Item.redstone,
          Character.valueOf('N'), Item.flintAndSteel,
          Character.valueOf('S'), Block.stone);
       
       //Gitter
       GameRegistry.registerBlock(gitter, "MetalMesh");
       LanguageRegistry.addName(gitter,  "Metal Mesh");
       GameRegistry.addRecipe(new ItemStack(gitter),
           "XX" , "XX" , Character.valueOf('X'), Wecraft.metalStick);
       
       //Drill Head
       GameRegistry.registerBlock(drillHead, "DrillHead");
       LanguageRegistry.addName(drillHead, "Drill-Head");
       
       //Time Sensor
       GameRegistry.registerBlock(sensorTime, "TimeSensor");
       LanguageRegistry.addName(sensorTime, "Time Sensor");
       GameRegistry.addRecipe(new ItemStack(sensorTime),
           "XCX" , "W#W" , "WWW" ,
           Character.valueOf('X'), Wecraft.metalStick,
           Character.valueOf('C'), Item.pocketSundial,
           Character.valueOf('#'), Item.redstone,
           Character.valueOf('W'), Block.planks);
       
       //Burner
       GameRegistry.registerBlock(grill, "Burner");
       LanguageRegistry.addName(grill, "Burner");
       GameRegistry.addRecipe(new ItemStack(grill),
           "XXX" , "S#S" , "SSS" ,
           Character.valueOf('X'), Wecraft.metalStick,
           Character.valueOf('S'), Block.cobblestone,
           Character.valueOf('#'), Block.furnaceIdle);
       
       //Drill Head Item
       GameRegistry.registerItem(drillHeadItem, "DrillHeadItem");
       LanguageRegistry.addName(drillHeadItem, "Drill Head");  
       GameRegistry.addRecipe(new ItemStack(drillHeadItem),
           " X " , "###" , " # " ,
           Character.valueOf('X'), Wecraft.metalStick, Character.valueOf('#'), Item.ingotIron); 
       
       //Water Tank
       GameRegistry.registerBlock(waterMaker, "WaterTank");
       LanguageRegistry.addName(waterMaker,  "Water Tank");
       GameRegistry.addRecipe(new ItemStack(waterMaker),
           "X X" , "S#S" , "SSS" ,
           Character.valueOf('X'), Wecraft.metalStick,
           Character.valueOf('S'), Block.stone,
           Character.valueOf('#'), Item.bucketEmpty);
       
       //Metal Stick
       GameRegistry.registerItem(metalStick,"MetalStick");
       LanguageRegistry.addName(metalStick, "Metal Stick");  
       GameRegistry.addRecipe(new ItemStack(metalStick, 8),
           "XXX" , "X#X" , "XXX" ,
           Character.valueOf('X'), Item.stick, Character.valueOf('#'), Item.ingotIron);
    }
}
  