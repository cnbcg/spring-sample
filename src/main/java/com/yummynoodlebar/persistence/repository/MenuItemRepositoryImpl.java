package com.yummynoodlebar.persistence.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.stereotype.Component;

@Component
public class MenuItemRepositoryImpl implements AnalyseIngredients {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Map<String, Integer> analyseIngredientsByPopularity() {
		MapReduceResults<IngredientAnalysis> results = mongoTemplate.mapReduce("menu", "classpath:ingredientsmap.js",
				"classpath:ingredientsreduce.js", IngredientAnalysis.class);

		Map<String, Integer> analysis = new HashMap<String, Integer>();

		for (IngredientAnalysis ingredientAnalysis : results) {
			analysis.put(ingredientAnalysis.getId(), ingredientAnalysis.getValue());
		}

		return analysis;
	}

	private static class IngredientAnalysis {
		private String id;
		private int value;

		@SuppressWarnings("unused")
		private void setId(String name) {
			this.id = name;
		}

		@SuppressWarnings("unused")
		private void setValue(int count) {
			this.value = count;
		}

		public String getId() {
			return id;
		}

		public int getValue() {
			return value;
		}
	}
}
