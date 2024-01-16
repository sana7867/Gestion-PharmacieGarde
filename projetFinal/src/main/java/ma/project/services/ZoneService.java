package ma.project.services;

import ma.project.dao.IDao;
import ma.project.entities.Zone;
import ma.project.repositories.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService implements IDao<Zone> {

	@Autowired
	private ZoneRepository zr;
	
	@Override
	public Zone create(Zone o) {
		// TODO Auto-generated method stub
		return zr.save(o);
	}

	@Override
	public Zone update(Zone o) {
		// TODO Auto-generated method stub
		return zr.save(o);
	}

	@Override
	public boolean delete(Zone o) {
		 try {
	            zr.delete(o);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	}

	@Override
	public List<Zone> findAll() {
		// TODO Auto-generated method stub
		return zr.findAll();
	}

	@Override
	public Zone findById(Long id) {
		// TODO Auto-generated method stub
		return zr.findById(id).orElse(null);
	}
	public List<Zone> findByVille(String villeNom){
		return zr.findByVilleNom(villeNom);
	}
}

