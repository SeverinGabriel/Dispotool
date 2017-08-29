
package ch.gabrieltransport.auftragverwaltung.dal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;

import com.xdev.dal.JPADAO;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrer;
import ch.gabrieltransport.auftragverwaltung.entities.Fahrerfunktion;

/**
 * Home object for domain model class Fahrer.
 * 
 * @see Fahrer
 */
public class FahrerDAO extends JPADAO<Fahrer, Integer> {
	public FahrerDAO() {
		super(Fahrer.class);
			}
	
	@Transactional
	public List<Fahrer> findAllByFunktion(Fahrerfunktion funktion){
		Session session = this.getSession(); 
		String hql = "SELECT f FROM Fahrer f JOIN f.fahrerfunktionmaps fm WHERE fm.fahrerfunktion = :funktion";
		List<Fahrer> driver = session.createQuery(hql)
				.setParameter("funktion", funktion)
				.list();
		return driver;
	
	}
	
	@Transactional
	public List<Fahrer> findAllByFunctionSet(Set<Fahrerfunktion> functions){
		if(functions != null && !functions.isEmpty()){
			Session session = this.getSession(); 
			String hql = "SELECT f FROM Fahrer f JOIN f.fahrerfunktionmaps fm WHERE fm.fahrerfunktion in :functions order by f.name";
			List<Fahrer> driver = session.createQuery(hql)
				.setParameter("functions", functions)
				.list();
			return driver;
		}
		else{
			return new ArrayList<Fahrer>();
		}
	}
	
}