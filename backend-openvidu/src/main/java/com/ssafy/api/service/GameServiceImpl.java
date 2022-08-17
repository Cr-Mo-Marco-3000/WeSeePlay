package com.ssafy.api.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.CallMyNameWord;
import com.ssafy.db.entity.LiarWord;
import com.ssafy.db.repository.CallMyNameRepository;
import com.ssafy.db.repository.LiarWordRepository;

@Service("gameService")
public class GameServiceImpl implements GameService {

	@Autowired
	LiarWordRepository liarWordRepository;
	
	Random rand=new Random();

	@Autowired
	CallMyNameRepository callMyNameRepository;

	@Override
	public String getWord(int liarGenre) {
		List<LiarWord> words=liarWordRepository.findAllByGenreId(liarGenre);
		if(words.isEmpty()) {
			return null;
		}
		return words.get(rand.nextInt(words.size())).getWord();
	}

	@Override
	public List<String> getCallMyNameWord() {
		List<CallMyNameWord> wordList = callMyNameRepository.findAll();
		List<String> result = new ArrayList<String>();
		
		Collections.shuffle(wordList);
		for (int i = 0; i < 3; i++) {
			result.add(wordList.get(i).getWord());
		}
		
		return result;
	}
	
	

}
