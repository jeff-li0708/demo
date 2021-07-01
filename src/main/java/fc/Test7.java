package fc;

import bean.User;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;

import javax.annotation.Nullable;

public class Test7 {
    public static void main(String[] args) {
        User a=null;
        Optional<User> absent = Optional.absent();
        boolean or = absent.isPresent();
        Preconditions.checkArgument(3>2,"error");
        Ordering<User> ordering = Ordering.natural().onResultOf(new Function<User, Comparable>() {
            @Nullable
            @Override
            public Comparable apply(@Nullable User user) {
                return user.getSeed();
            }
        });
        User u1 = new User(1L,"张三");
        User u2 = new User(2L,"李四");
        Ordering<Integer> ordering1 = Ordering.natural();
        User min = ordering.min(u1,u2);

        System.out.printf("or");

    }
}
