package com.ssafy.api.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.LiarWord;
import com.ssafy.db.repository.LiarWordRepository;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
	LiarWordRepository liarWordRepository;
	
	@Override
	public String getWord(int liarGenre) {
		Random rand = new Random();
		List<LiarWord> words=liarWordRepository.findAllByGenreId(liarGenre);
		if(words.size()==0) {
			return null;
		}
		return words.get(rand.nextInt(words.size())).getWord();
	}

}
