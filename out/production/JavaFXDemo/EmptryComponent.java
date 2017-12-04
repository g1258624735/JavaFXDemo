package lib;

import dagger.Component;

/**
 * Created by gxj on 2017/11/22.
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class
        , modules = {EmptryModule.class})
public interface EmptryComponent extends BaseFragmentComponent<EmptryFragment> {

}
