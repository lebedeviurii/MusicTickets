package mt.service;

import mt.dao.BandDao;
import mt.model.Band;
import mt.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BandService {

    private final BandDao dao;

    @Autowired
    public BandService(BandDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void persist(Band band) {
        dao.persist(band);
    }

    @Transactional(readOnly = true)
    public Band find(Long id) {
        return dao.find(id);
    }

    @Transactional
    public Band update(Band band, Long id){
        Objects.requireNonNull(band);
        return dao.update(band, id);
    }

    @Transactional
    public void remove(Band band){
        Objects.requireNonNull(band);
        dao.remove(band);
    }

    public List<Band> findAll() {
        return dao.findAll();
    }

    public List<Band> findAllByEvent(Event byId) {
        return dao.findAllByEvent(byId.getId());
    }
}
