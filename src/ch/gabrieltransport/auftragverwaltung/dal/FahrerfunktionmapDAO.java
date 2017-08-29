
package ch.gabrieltransport.auftragverwaltung.dal;

import org.hibernate.Session;

import com.xdev.dal.JPADAO;

import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktionmap;

/**
 * Home object for domain model class Fahrerfunktionmap.
 * 
 * @see Fahrerfunktionmap
 */
public class FahrerfunktionmapDAO extends JPADAO<Fahrerfunktionmap, Integer> {
	public FahrerfunktionmapDAO() {
		super(Fahrerfunktionmap.class);
	}
	
	public void deleteByFahrer(Fahrer fahrer){
		fahrer.getFahrerfunktionmaps().forEach(m -> this.remove(m));
}
}