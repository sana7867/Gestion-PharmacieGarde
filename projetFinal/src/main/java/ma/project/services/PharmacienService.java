package ma.project.services;

import ma.project.dao.IDao;
import ma.project.entities.Pharmacien;
import ma.project.repositories.PharmacienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacienService implements IDao<Pharmacien> {

	@Autowired
	private PharmacienRepository pharmacienRepository;

	@Override
	public Pharmacien create(Pharmacien pharmacien) {
		return pharmacienRepository.save(pharmacien);
	}

	@Override
	public Pharmacien update(Pharmacien pharmacien) {
		return pharmacienRepository.save(pharmacien);
	}

	@Override
	public boolean delete(Pharmacien pharmacien) {
		try {
			pharmacienRepository.delete(pharmacien);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Pharmacien> findAll() {
		return pharmacienRepository.findAll();
	}

	@Override
	public Pharmacien findById(Long id) {
		return pharmacienRepository.findById(id).orElse(null);
	}

	public Pharmacien findByEmail(String email){
		return  pharmacienRepository.findByEmail(email);
	}
}
