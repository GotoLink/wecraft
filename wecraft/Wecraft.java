package wecraft;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = "wecraft", name = "W3(r4ft", version = "0.2")
public class Wecraft {
	public static int convertyTexture;
	//public static final Block saverItem;
	public static Block conveyorBelt, sensorArrow, drill, sensorFire, gitter, drillHead;
	public static Item metalStick, drillHeadItem;
	public static Block sensorTime, grill, waterMaker;
	public static int waterTankRadiusWide, waterTankRadiusDepth;

	@EventHandler
	public void preload(FMLPreInitializationEvent event) {
		CreativeTabs tab = new CreativeTabs("W3(r4ft") {
			@Override
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem() {
				return drillHeadItem;
			}
		};
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		// saverItem = new BlockWtSaverItem(cfg.get("ItemSaverID", 3170), 2).setHardness(0.5F).setResistance(1.0F).setBlockName("Item Saver").setStepSound(Block.soundSandFootstep);
		conveyorBelt = new BlockConveyorBelt().func_149711_c(0.5F).func_149711_c(3.0F).func_149658_d("wecraft:ConvertyBelt/ConveyorBelt_")
				.func_149663_c("Conveyor Belt").func_149672_a(Block.field_149777_j).func_149647_a(tab);
		sensorArrow = new BlockSensorArrow().func_149711_c(0.3F).func_149711_c(2.0F).func_149658_d("wecraft:SensorArrow").func_149663_c("Arrow Sensor")
				.func_149672_a(Block.field_149775_l).func_149647_a(tab);
		drill = new BlockDrill().func_149711_c(1.0F).func_149711_c(2000.0F).func_149658_d("wecraft:Drill").func_149663_c("Drill")
				.func_149672_a(Block.field_149766_f).func_149647_a(tab);
		sensorFire = new BlockSensorFire().func_149711_c(4.0F).func_149711_c(7.0F).func_149658_d("wecraft:SensorFire").func_149663_c("Fire Sensor")
				.func_149672_a(Block.field_149780_i).func_149647_a(tab);
		gitter = new BlockGitter().func_149711_c(0.4F).func_149711_c(1.0F).func_149658_d("wecraft:gitter").func_149663_c("Metal Mesh")
				.func_149672_a(Block.field_149777_j).func_149647_a(tab);
		drillHead = new BlockDrillHead().func_149711_c(9.0F).func_149711_c(9.0F).func_149658_d("iron_block").func_149663_c("Drill-Head")
				.func_149672_a(Block.field_149780_i);
		sensorTime = new BlockSensorTime().func_149711_c(2.0F).func_149711_c(5.0F).func_149658_d("wecraft:Sensor").func_149663_c("Time Sensor")
				.func_149672_a(Block.field_149766_f).func_149647_a(tab);
		grill = new BlockGrill().func_149711_c(1.0F).func_149711_c(5.0F).func_149658_d("wecraft:grill").func_149663_c("Burner")
				.func_149672_a(Block.field_149777_j).func_149647_a(tab);
		waterMaker = new BlockWaterMaker().func_149711_c(3.0F).func_149711_c(7.0F).func_149658_d("wecraft:WaterMaker").func_149663_c("Water Tank")
				.func_149672_a(Block.field_149780_i).func_149647_a(tab);
		drillHeadItem = new Item().setTextureName("wecraft:DrillHead").setUnlocalizedName("Drill Head").setMaxStackSize(1).setCreativeTab(tab);
		metalStick = new Item().setTextureName("wecraft:MetalStick").setUnlocalizedName("Metal Stick").setMaxStackSize(64).setCreativeTab(tab);
		waterTankRadiusWide = cfg.get("general", "WaterTankRadiusWide", 5).getInt();
		waterTankRadiusDepth = cfg.get("general", "WaterTankRadiusDepth", 5).getInt();
		convertyTexture = cfg.get("general", "ConvertyBeltAnimation", 1).getInt();
        if(cfg.hasChanged())
		    cfg.save();
		/*
		 * //Itemsaver GameRegistry.registerBlock(saverItem, "ItemSaver");
		 * LanguageRegistry.addName(saverItem, "Item Saver");
		 * GameRegistry.addRecipe(new ItemStack(saverItem, 1), new Object[] {
		 * "XXX" , "X S", "XXX" , Character.valueOf('X'), Block.planks,
		 * Character.valueOf('S'), mod_Wecraft.gitter });
		 */
		//ConvertyBelt
		GameRegistry.registerBlock(conveyorBelt, "ConveyorBelt");
		GameRegistry.addRecipe(new ItemStack(conveyorBelt, 6), "XXX", "S#S", "XXX", Character.valueOf('X'), Wecraft.metalStick, Character.valueOf('#'), Items.redstone, Character.valueOf('S'),
				Blocks.cobblestone);
		//Arrow Sensor
		GameRegistry.registerBlock(sensorArrow, "ArrowTarget");
		GameRegistry.addRecipe(new ItemStack(sensorArrow), "XXX", "S#S", "XXX", Character.valueOf('X'), Blocks.wool, Character.valueOf('S'), Wecraft.gitter, Character.valueOf('#'), Items.arrow);
		//Drill
		GameRegistry.registerBlock(drill, "Drill");
		GameRegistry.addRecipe(new ItemStack(drill), "X X", "S#S", "XDX", Character.valueOf('X'), Blocks.planks, Character.valueOf('S'), Items.redstone, Character.valueOf('#'), Items.diamond,
				Character.valueOf('D'), Wecraft.drillHeadItem);
		//Fire Sensor
		GameRegistry.registerBlock(sensorFire, "FireAlarm");
		GameRegistry.addRecipe(new ItemStack(sensorFire), "SSS", "SNS", "X#X", Character.valueOf('X'), Wecraft.metalStick, Character.valueOf('#'), Items.redstone, Character.valueOf('N'),
				Blocks.netherrack, Character.valueOf('S'), Blocks.stone);
		GameRegistry.addRecipe(new ItemStack(sensorFire), "SSS", "SNS", "X#X", Character.valueOf('X'), Wecraft.metalStick, Character.valueOf('#'), Items.redstone, Character.valueOf('N'),
				Items.flint_and_steel, Character.valueOf('S'), Blocks.stone);
		//Gitter
		GameRegistry.registerBlock(gitter, "MetalMesh");
		GameRegistry.addRecipe(new ItemStack(gitter), "XX", "XX", Character.valueOf('X'), Wecraft.metalStick);
		//Drill Head
		GameRegistry.registerBlock(drillHead, "DrillHead");
		//Time Sensor
		GameRegistry.registerBlock(sensorTime, "TimeSensor");
		GameRegistry.addRecipe(new ItemStack(sensorTime), "XCX", "W#W", "WWW", Character.valueOf('X'), Wecraft.metalStick, Character.valueOf('C'), Items.clock, Character.valueOf('#'),
				Items.redstone, Character.valueOf('W'), Blocks.planks);
		//Burner
		GameRegistry.registerBlock(grill, "Burner");
		GameRegistry.addRecipe(new ItemStack(grill), "XXX", "S#S", "SSS", Character.valueOf('X'), Wecraft.metalStick, Character.valueOf('S'), Blocks.cobblestone, Character.valueOf('#'),
				Blocks.furnace);
		//Drill Head Item
		GameRegistry.registerItem(drillHeadItem, "DrillHeadItem");
		GameRegistry.addRecipe(new ItemStack(drillHeadItem), " X ", "###", " # ", Character.valueOf('X'), Wecraft.metalStick, Character.valueOf('#'), Items.iron_ingot);
		//Water Tank
		GameRegistry.registerBlock(waterMaker, "WaterTank");
		GameRegistry.addRecipe(new ItemStack(waterMaker), "X X", "S#S", "SSS", Character.valueOf('X'), Wecraft.metalStick, Character.valueOf('S'), Blocks.stone, Character.valueOf('#'),
				Items.bucket);
		//Metal Stick
		GameRegistry.registerItem(metalStick, "MetalStick");
		GameRegistry.addRecipe(new ItemStack(metalStick, 8), "XXX", "X#X", "XXX", Character.valueOf('X'), Items.stick, Character.valueOf('#'), Items.iron_ingot);
	}
}
