package com.ncc.kitsNccUnit.implementation;

import com.ncc.kitsNccUnit.convertor.CampRegistrationConvertor;
import com.ncc.kitsNccUnit.dto.CampRegistrationDTO;
import com.ncc.kitsNccUnit.dto.PublicCampRegistrationDTO;
import com.ncc.kitsNccUnit.entity.CampRegistrations;
import com.ncc.kitsNccUnit.entity.Camps;
import com.ncc.kitsNccUnit.entity.Cadet;
import com.ncc.kitsNccUnit.exceptions.DuplicateRegistrationException;
import com.ncc.kitsNccUnit.exceptions.ResourceNotFoundException;
import com.ncc.kitsNccUnit.repository.CampRegistrationRepository;
import com.ncc.kitsNccUnit.repository.CampsRepository;
import com.ncc.kitsNccUnit.repository.CadetRepository;
import com.ncc.kitsNccUnit.service.CampRegistrationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CampRegistrationServiceImpl implements CampRegistrationService {

    @Autowired private CampRegistrationRepository registrationRepo;
    @Autowired private CampsRepository campsRepo;
    @Autowired private CadetRepository cadetRepo;
    @Autowired private CampRegistrationConvertor convertor;


    @Override
    public CampRegistrationDTO registerForCamp(CampRegistrationDTO dto) throws DuplicateRegistrationException {
        Camps camp = campsRepo.findById(dto.getCampId())
                .orElseThrow(() -> new ResourceNotFoundException("Camp not found"));
        Cadet cadet = cadetRepo.findById(dto.getCadetId())
                .orElseThrow(() -> new ResourceNotFoundException("Cadet not found"));

        // Check if cadet is already registered for this camp
        if (registrationRepo.existsByCampAndCadet(camp, cadet)) {
            throw new DuplicateRegistrationException("Cadet is already registered for this camp");
        }

        CampRegistrations entity = convertor.toEntity(dto, camp, cadet);
        return convertor.toDTO(registrationRepo.save(entity));
    }

    @Override
    public List<CampRegistrationDTO> getAllRegistrations() {
        return registrationRepo.findAll()
                .stream()
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CampRegistrationDTO getRegistrationById(int id) {
        return registrationRepo.findById(id).map(convertor::toDTO).orElse(null);
    }

    @Override
    public boolean registerWithoutLogin(PublicCampRegistrationDTO dto) {
        Optional<Cadet> cadetOpt = cadetRepo.findByRegimentalNoAndGenderAndMobileNoAndBtechYearAndBranch(
                dto.getRegimentalNo(), dto.getGender(), dto.getMobileNo(),
                dto.getBtechYear(), dto.getBranch());

        if (cadetOpt.isEmpty()) return false;

        Optional<Camps> campOpt = campsRepo.findById(dto.getCampId());
        if (campOpt.isEmpty()) return false;

        CampRegistrations registration = new CampRegistrations();
        registration.setCadet(cadetOpt.get());
        registration.setCamp(campOpt.get());
        registration.setIsAccepted(false); // default

        registrationRepo.save(registration);
        return true;
    }

//    @Override
//    public boolean acceptCadetToCamp(int registrationId, boolean isAccepted) {
//        CampRegistrations optional = registrationRepo.findById(registrationId).orElseThrow(() -> new RuntimeException("didn't find any registrations"));
//        System.out.println(optional);
//        if (optional != null) {
//            int no = isAccepted ? 1 : 0;
//            registrationRepo.updateIsAccepted(registrationId, no);
//        }
//        return false;
//    }

    @Override
    @Transactional
    public boolean acceptCadetToCamp(int registrationId, boolean isAccepted) {
        registrationRepo.updateIsAccepted(registrationId, isAccepted);
        return true;
    }

    @Override
    public List<CampRegistrationDTO> getCadetsByCampId(int campId) {
        List<CampRegistrations> registrations = registrationRepo.findByCampId(campId);
        return registrations.stream()
                .map(reg -> new CampRegistrationDTO(
                        reg.getId(),
                        reg.getCamp().getId(),
                        reg.getCadet().getId(),
                        reg.getIsAccepted()))
                .toList();
    }

    @Override
    public List<CampRegistrationDTO> getRegistrationsByCadetId(int cadetId) {
        List<CampRegistrations> registrations = registrationRepo.findByCadetId(cadetId);
        return registrations.stream()
                .map(convertor::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CampRegistrationDTO> getAcceptedCadetsByCampId(int campId) {
        List<CampRegistrations> registrations = registrationRepo.findByCampIdAndIsAcceptedTrue(campId);
        return mapToDTOList(registrations);
    }

    @Override
    public List<CampRegistrationDTO> getPendingCadetsByCampId(int campId) {
        List<CampRegistrations> registrations = registrationRepo.findByCampIdAndIsAcceptedFalse(campId);
        return mapToDTOList(registrations);
    }

    private List<CampRegistrationDTO> mapToDTOList(List<CampRegistrations> registrations) {
        return registrations.stream()
                .map(reg -> new CampRegistrationDTO(
                        reg.getId(),
                        reg.getCamp().getId(),
                        reg.getCadet().getId(),
                        reg.getIsAccepted()))
                .toList();
    }

}
