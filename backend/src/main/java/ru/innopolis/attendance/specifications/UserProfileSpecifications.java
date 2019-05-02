package ru.innopolis.attendance.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.innopolis.attendance.models.Role;
import ru.innopolis.attendance.models.UserProfile;
import ru.innopolis.attendance.models.UserProfile_;

import java.time.LocalDate;
import java.util.Collection;

public class UserProfileSpecifications {

    public static Specification<UserProfile> getUserWithRoles(Collection<Role> roles) {
        return (root, query, criteriaBuilder) ->
                roles == null ? null :
                        criteriaBuilder.isTrue(root.get(UserProfile_.role).in(roles));
    }

    public static Specification<UserProfile> getUserWithGroupNumbers(Collection<Short> groups) {
        return (root, query, criteriaBuilder) ->
                groups == null ? null :
                        criteriaBuilder.isTrue(root.get(UserProfile_.groupNumber).in(groups));
    }

    public static Specification<UserProfile> getUserWithBirthdayAfter(LocalDate birthday) {
        return (root, query, criteriaBuilder) ->
                birthday == null ? null :
                        criteriaBuilder.greaterThanOrEqualTo(root.get(UserProfile_.birthday), birthday);
    }

    public static Specification<UserProfile> getUserWithBirthdayBefore(LocalDate birthday) {
        return (root, query, criteriaBuilder) ->
                birthday == null ? null :
                        criteriaBuilder.lessThanOrEqualTo(root.get(UserProfile_.birthday), birthday);
    }

    public static Specification<UserProfile> getUserWithFirstName(String firstName) {
        return (root, query, criteriaBuilder) ->
                firstName == null ? null :
                        criteriaBuilder.like(root.get(UserProfile_.name),
                                "%" + firstName.toLowerCase() + "%");
    }

    public static Specification<UserProfile> getUserWithLastName(String surname) {
        return (root, query, criteriaBuilder) ->
                surname == null ? null :
                        criteriaBuilder.like(root.get(UserProfile_.surname),
                                "%" + surname.toLowerCase() + "%");
    }
}
