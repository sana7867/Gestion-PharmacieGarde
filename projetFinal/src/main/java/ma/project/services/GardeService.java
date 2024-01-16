package ma.project.services;

import ma.project.dao.IDao;
import ma.project.entities.Garde;
import ma.project.entities.Pharmacie;
import ma.project.repositories.GardeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GardeService implements IDao<Garde> {

	@Autowired
	private GardeRepository gr;
	
	@Override
	public Garde create(Garde o) {
		return gr.save(o);
	}

	@Override
	public Garde update(Garde o) {
		
		return gr.save(o);
	}

	@Override
	public boolean delete(Garde o) {
		 try {
	            gr.delete(o);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	}

	@Override
	public List<Garde> findAll() {
		// TODO Auto-generated method stub
		return gr.findAll();
	}

	@Override
	public Garde findById(Long id) {
		// TODO Auto-generated method stub
		return gr.findById(id).orElse(null);
	}
	public List<Garde>findByPharamcies(Pharmacie pharmacie){
		return gr.findGardeByPharmacies(pharmacie);
	}

}
