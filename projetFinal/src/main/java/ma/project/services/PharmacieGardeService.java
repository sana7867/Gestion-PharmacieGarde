package ma.project.services;

import ma.project.entities.PharmacieGarde;
import ma.project.repositories.PharmacieGardeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PharmacieGardeService {

	@Autowired
	private PharmacieGardeRepository pgr;

	@Transactional  // Add transactional annotation
	public PharmacieGarde create(PharmacieGarde o) {
		return pgr.save(o);
	}

	@Transactional  // Add transactional annotation
	public PharmacieGarde update(PharmacieGarde o) {
		return pgr.save(o);
	}

	@Transactional  // Add transactional annotation
	public boolean delete(PharmacieGarde o) {
		try {
			pgr.delete(o);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public List<PharmacieGarde> findAll() {
		return pgr.findAll();
	}

	public PharmacieGarde findById(Long id) {
		return pgr.findById(id).orElse(null);
	}
	public  List<PharmacieGarde> findByPharmacieId(Long pharmacieId){
		return pgr.findByPharmacieId(pharmacieId);
	}


}
