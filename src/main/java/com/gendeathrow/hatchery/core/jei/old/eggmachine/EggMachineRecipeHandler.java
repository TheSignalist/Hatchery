package com.gendeathrow.hatchery.core.jei.old.eggmachine;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class EggMachineRecipeHandler implements IRecipeHandler<EggMachineWrapper> 
{


	@Override
	public String getRecipeCategoryUid(EggMachineWrapper arg0) 
	{
		return EggMachineCategory.UID;
	}

	@Override
	public Class<EggMachineWrapper> getRecipeClass() 
	{
		return EggMachineWrapper.class;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(EggMachineWrapper recipe) 
	{
		return recipe;
	}

	@Override
	public boolean isRecipeValid(EggMachineWrapper recipe) 
	{
		return recipe.getInput().size() > 0 && recipe.getOutput().size() > 0;
	}

}