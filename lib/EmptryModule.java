package lib;
import javax.annotation.Nonnull;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by gxj on 2017/11/22
 */
@Module
public class EmptryModule {


    @Provides
    public EmptryViewState providesEmptryViewState() {
        return new EmptryViewState();
    }

    @Provides
    public EmptryPresenter providesEmptryPresenter(GroupService service) {
        return new EmptryPresenter(service);
    }

    @Provides
    public GroupService providesGroupService(@Nonnull Retrofit retrofit) {
        return retrofit.create(GroupService.class);
    }
}
