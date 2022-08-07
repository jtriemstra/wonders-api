package com.jtriemstra.wonders.api.model.resource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EvalInfo {
	List<ResourceWithTradeCost> resources;
	String name;		
}