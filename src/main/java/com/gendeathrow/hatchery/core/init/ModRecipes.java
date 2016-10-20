package com.gendeathrow.hatchery.core.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModRecipes 
{

	public static void RegisterOreDic()
	{
		OreDictionary.registerOre("dirt", ModBlocks.fertlizedDirt);
		OreDictionary.registerOre("manure", ModItems.manure);
	}
	
	public static void RegisterRecipes()
	{
	    IRecipe animalNetRecipe = new ShapedOreRecipe(new ItemStack(ModItems.animalNet), new Object[] {
            "xSS",
            "SAA",
            "xxA",
            'S', "stickWood",   // can use ordinary items, blocks, itemstacks in ShapedOreRecipe
            'A', "string",  // look in OreDictionary for vanilla definitions
	    });
	    GameRegistry.addRecipe(animalNetRecipe);
	    
	    IRecipe penRecipe = new ShapedOreRecipe(new ItemStack(ModBlocks.pen), new Object[] {	    
	    	"WxW", 
	    	"WNW", 
	    	"WWW",
	    	'W', "plankWood", 
	    	'N', ModBlocks.nest
	    });
	    
	    GameRegistry.addRecipe(penRecipe);
	    
	    IRecipe fertDirtRecipe = new ShapedOreRecipe(new ItemStack(ModBlocks.fertlizedDirt), new Object[] {
	    	"PPP", 
	    	"PDP", 
	    	"PPP",
	    	'P', "manure",
	    	'D', "dirt"
		});
	    GameRegistry.addRecipe(fertDirtRecipe);
	    
	    IRecipe feederRecipe = new ShapedOreRecipe(new ItemStack(ModBlocks.feeder), new Object[] {
	    	".I.", 
	    	"SBS", 
	    	"SSS",
	    	'I', "ingotIron",
	    	'B', "blockIron",
	    	'S', "slabWood"
		});
	    GameRegistry.addRecipe(feederRecipe);
	    
		GameRegistry.addRecipe(new ItemStack(ModBlocks.nest), "xxx", "AxA", "AAA",'A', Blocks.HAY_BLOCK);

	    IRecipe sprayerRecipe = new ShapedOreRecipe(new ItemStack(ModBlocks.feeder), new Object[] {
	    	"LD.", 
	    	".IB", 
	    	"IGI",
	    	'I', "ingotIron",
	    	'G', "blockGlass",
	    	'D',  new ItemStack(Blocks.HARDENED_CLAY,1,13),
	    	'L',  new ItemStack(Blocks.HARDENED_CLAY,1,5)
	    	
		});
		
	    GameRegistry.addRecipe(sprayerRecipe);
	    
	    NBTTagCompound tag = new NBTTagCompound();
	    tag.setString("FluidName", "liquid_fertilizer");
	    tag.setInteger("Amount", 1000);
	    ItemStack bucket = new ItemStack(Items.BUCKET);
	    bucket.setTagCompound(tag);
	    
	    
	    IRecipe bucketFert = new ShapedOreRecipe(ModFluids.getFertilizerBucket(), new Object[] {
	    	"FFF", 
	    	"FBF", 
	    	"FFF",
	    	'F', ModBlocks.fertlizedDirt,
	    	'B',  Items.WATER_BUCKET
	    	
		}); 
	    GameRegistry.addRecipe(bucketFert);


		// GameRegistry.addRecipe(new ItemStack(Items.EGG), new Object[]{ ModItems.hatcheryEgg });
	}
}
