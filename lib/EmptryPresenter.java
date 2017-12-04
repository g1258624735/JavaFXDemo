package lib;

/**
 * Created by gxj on 2017/11/22.
 *
 */

public class EmptryPresenter extends BasePresenter<EmptryView> {

    private final GroupService groupService;

    public EmptryPresenter(GroupService groupService) {
        this.groupService = groupService;
    }

}
