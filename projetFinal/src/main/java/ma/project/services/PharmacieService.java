package ma.project.services;

import ma.project.dao.IDao;
import ma.project.entities.Pharmacie;
import ma.project.repositories.PharmacieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PharmacieService implements IDao<Pharmacie> {

	@Autowired
	private PharmacieRepository pr;
	
	@Override
	public Pharmacie create(Pharmacie o) {
		// TODO Auto-generated method stub
		return pr.save(o);
	}

	@Override
	public Pharmacie update(Pharmacie o) {
		// TODO Auto-generated method stub
		return pr.save(o);
	}

	@Override
	public boolean delete(Pharmacie o) {
		 try {
	            pr.delete(o);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	}

	@Override
	public List<Pharmacie> findAll() {
		// TODO Auto-generated method stub
		return pr.findAll();
	}

	@Override
	public Pharmacie findById(Long id) {
		// TODO Auto-generated method stub
		return pr.findById(id).orElse(null);
	}
	public List<Pharmacie> findByZone(Long zoneId){
		return pr.findByZoneId(zoneId);
	}
	public List<Pharmacie> findZoneVilleNom(String villeNom){

		return pr.findByZoneVilleNom(villeNom);
	}
	public List<Pharmacie> findByZoneIdAndZoneVilleNom(Long zoneId,String villeNom){

		return pr.findByZoneIdAndZoneVilleNom(zoneId,villeNom);
	}

	public List<Pharmacie> findByUsersId(Long usersId){
		return pr.findByUsersId(usersId);
	}
}

