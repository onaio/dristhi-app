package org.ei.drishti.view.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.ei.drishti.domain.EligibleCouple;
import org.ei.drishti.domain.Mother;
import org.ei.drishti.repository.AllBeneficiaries;
import org.ei.drishti.repository.AllEligibleCouples;
import org.ei.drishti.util.Cache;
import org.ei.drishti.view.contract.FPClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.ei.drishti.util.EasyMap.mapOf;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FPSmartRegistryControllerTest {
    @Mock
    private AllEligibleCouples allEligibleCouples;
    @Mock
    private AllBeneficiaries allBeneficiaries;
    private FPSmartRegistryController controller;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        controller = new FPSmartRegistryController(allEligibleCouples, allBeneficiaries, new Cache<String>());
    }

    @Test
    public void shouldSortECsByPriorityAndThenByName() throws Exception {
        EligibleCouple ecNormalPriority1 = new EligibleCouple("EC Case 1", "Woman A", "Husband A", "EC Number 1", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecNormalPriority2 = new EligibleCouple("EC Case 2", "Woman B", "Husband B", "EC Number 2", "kavalu_hosur", "Bherya SC", normalPriority());
        EligibleCouple ecNormalPriority3 = new EligibleCouple("EC Case 3", "Woman C", "Husband C", "EC Number 3", "Bherya", "Bherya SC", normalPriority());
        EligibleCouple ecHighPriority1 = new EligibleCouple("EC Case 4", "Woman D", "Husband D", "EC Number 4", "Bherya", "Bherya SC", highPriority());
        EligibleCouple ecHighPriority2 = new EligibleCouple("EC Case 5", "Woman E", "Husband E", "EC Number 5", "kavalu_hosur", "Bherya SC", highPriority());
        EligibleCouple ecHighPriority3 = new EligibleCouple("EC Case 6", "Woman F", "Husband F", "EC Number 6", "Bherya", "Bherya SC", highPriority());
        Mother motherForNormalPriorityEC1 = new Mother("MOTHER Case 1", "EC Case 1", "12345", "2012-12-12");
        Mother motherForHighPriorityEC1 = new Mother("MOTHER Case 4", "EC Case 4", "4444", "2012-12-22");
        when(allEligibleCouples.all()).thenReturn(asList(ecHighPriority3, ecNormalPriority2, ecHighPriority1, ecNormalPriority3, ecNormalPriority1, ecHighPriority2));
        when(allBeneficiaries.findMotherByECCaseId("EC Case 1")).thenReturn(motherForNormalPriorityEC1);
        when(allBeneficiaries.findMotherByECCaseId("EC Case 4")).thenReturn(motherForHighPriorityEC1);
        FPClient expectedNormalPriorityClient1 = new FPClient("Woman A", "Husband A", null, "12345", "EC Number 1", "Bherya", null, null, null, null, null, null, null, null, null, false);
        FPClient expectedNormalPriorityClient2 = new FPClient("Woman B", "Husband B", null, "", "EC Number 2", "kavalu_hosur", null, null, null, null, null, null, null, null, null, false);
        FPClient expectedNormalPriorityClient3 = new FPClient("Woman C", "Husband C", null, "", "EC Number 3", "Bherya", null, null, null, null, null, null, null, null, null, false);
        FPClient expectedHighPriorityClient1 = new FPClient("Woman D", "Husband D", null, "4444", "EC Number 4", "Bherya", null, null, null, null, null, null, null, null, null, true);
        FPClient expectedHighPriorityClient2 = new FPClient("Woman E", "Husband E", null, "", "EC Number 5", "kavalu_hosur", null, null, null, null, null, null, null, null, null, true);
        FPClient expectedHighPriorityClient3 = new FPClient("Woman F", "Husband F", null, "", "EC Number 6", "Bherya", null, null, null, null, null, null, null, null, null, true);

        String clients = controller.get();

        List<FPClient> actualClients = new Gson().fromJson(clients, new TypeToken<List<FPClient>>() {
        }.getType());
        assertEquals(asList(
                expectedNormalPriorityClient1,
                expectedNormalPriorityClient2,
                expectedNormalPriorityClient3,
                expectedHighPriorityClient1,
                expectedHighPriorityClient2,
                expectedHighPriorityClient3
        ),

                actualClients);

    }

    private Map<String, String> normalPriority() {
        return mapOf("isHighPriority", "no");
    }

    private Map<String, String> highPriority() {
        return mapOf("isHighPriority", "yes");
    }

}
