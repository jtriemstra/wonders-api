package com.jtriemstra.wonders.api.model.deck.leaders;

import com.jtriemstra.wonders.api.model.card.Card;
import com.jtriemstra.wonders.api.model.card.leaders.Alexander;
import com.jtriemstra.wonders.api.model.card.leaders.Amytis;
import com.jtriemstra.wonders.api.model.card.leaders.Archimedes;
import com.jtriemstra.wonders.api.model.card.leaders.Aristotle;
import com.jtriemstra.wonders.api.model.card.leaders.Bilkis;
import com.jtriemstra.wonders.api.model.card.leaders.Caesar;
import com.jtriemstra.wonders.api.model.card.leaders.Cleopatra;
import com.jtriemstra.wonders.api.model.card.leaders.Croesus;
import com.jtriemstra.wonders.api.model.card.leaders.Euclid;
import com.jtriemstra.wonders.api.model.card.leaders.Hammurabi;
import com.jtriemstra.wonders.api.model.card.leaders.Hannibal;
import com.jtriemstra.wonders.api.model.card.leaders.Hatshepsut;
import com.jtriemstra.wonders.api.model.card.leaders.Hiram;
import com.jtriemstra.wonders.api.model.card.leaders.Hypatia;
import com.jtriemstra.wonders.api.model.card.leaders.Justinian;
import com.jtriemstra.wonders.api.model.card.leaders.Leonidas;
import com.jtriemstra.wonders.api.model.card.leaders.Maecenas;
import com.jtriemstra.wonders.api.model.card.leaders.Midas;
import com.jtriemstra.wonders.api.model.card.leaders.Nebuchadnezzar;
import com.jtriemstra.wonders.api.model.card.leaders.Nefertiti;
import com.jtriemstra.wonders.api.model.card.leaders.Nero;
import com.jtriemstra.wonders.api.model.card.leaders.Pericles;
import com.jtriemstra.wonders.api.model.card.leaders.Phidias;
import com.jtriemstra.wonders.api.model.card.leaders.Plato;
import com.jtriemstra.wonders.api.model.card.leaders.Praxiteles;
import com.jtriemstra.wonders.api.model.card.leaders.Ptolemy;
import com.jtriemstra.wonders.api.model.card.leaders.Pythagoras;
import com.jtriemstra.wonders.api.model.card.leaders.Ramses;
import com.jtriemstra.wonders.api.model.card.leaders.Sappho;
import com.jtriemstra.wonders.api.model.card.leaders.Solomon;
import com.jtriemstra.wonders.api.model.card.leaders.Varro;
import com.jtriemstra.wonders.api.model.card.leaders.Vitruvius;
import com.jtriemstra.wonders.api.model.card.leaders.Xenophon;
import com.jtriemstra.wonders.api.model.card.leaders.Zenobia;
import com.jtriemstra.wonders.api.model.deck.CardFactory;

public class LeaderCardFactory implements CardFactory {
	public Card[] getAllCards() {
		return new Card[] {
			new Alexander(),
			new Amytis(),
			new Archimedes(),
			new Aristotle(),
			new Bilkis(),
			new Caesar(),
			new Cleopatra(),
			new Croesus(),
			new Euclid(),
			new Hammurabi(),
			new Hannibal(),
			new Hatshepsut(),
			new Hiram(),
			new Hypatia(),
			new Justinian(),
			new Leonidas(),
			new Maecenas(),
			new Midas(),
			new Nebuchadnezzar(),
			new Nefertiti(),
			new Nero(),
			new Pericles(),
			new Phidias(),
			new Plato(),
			new Praxiteles(),
			new Ptolemy(),
			new Pythagoras(),
			new Ramses(),
			new Sappho(),
			new Solomon(),
			new Varro(),
			new Vitruvius(),
			new Xenophon(),
			new Zenobia()
		};
	}
}
