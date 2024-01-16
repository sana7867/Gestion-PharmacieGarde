package ma.project.services;

import ma.project.dao.IDao;
import ma.project.entities.Administrateur;
import ma.project.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdminService implements IDao<Administrateur> {

	@Autowired
	private AdminRepository ar;
	
	@Override
	public Administrateur create(Administrateur o) {
		// TODO Auto-generated method stub
		return ar.save(o);
	}

	@Override
	public Administrateur update(Administrateur o) {
		// TODO Auto-generated method stub
		return ar.save(o);
	}

	@Override
	public boolean delete(Administrateur o) {
		 try {
	            ar.delete(o);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	}

	@Override
	public List<Administrateur> findAll() {
		// TODO Auto-generated method stub
		return ar.findAll();
	}

	@Override
	public Administrateur findById(Long id) {
		// TODO Auto-generated method stub
		return ar.findById(id).orElse(null);
	}
	public Administrateur findByEmail(String email){
		return  ar.findByEmail(email);
	}
	public Administrateur findByNom(String nom){
		return  ar.findByNom(nom);
	}

}
