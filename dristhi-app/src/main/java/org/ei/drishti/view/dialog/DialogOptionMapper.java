package org.ei.drishti.view.dialog;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import org.ei.drishti.util.StringUtil;
import org.ei.drishti.view.contract.Village;

public class DialogOptionMapper {
    public Iterable<? extends DialogOption> mapToVillageFilterOptions(Iterable<Village> villages) {
        return Iterables.transform(villages, new Function<Village, DialogOption>() {
            @Override
            public DialogOption apply(Village village) {
                return new VillageFilter(StringUtil.humanize(village.name()));
            }
        });
    }
}
