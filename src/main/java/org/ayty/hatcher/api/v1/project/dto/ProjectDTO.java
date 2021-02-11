package org.ayty.hatcher.api.v1.project.dto;

import lombok.*;
import org.ayty.hatcher.api.v1.project.model.Project;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder
public class ProjectDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotEmpty
    @NonNull
    private Integer id;

    @NotEmpty
    @NonNull
    private String name;

    @NotEmpty
    @NonNull
    private String description;

    @NotEmpty
    @NonNull
    private String logo;

    @NotEmpty
    @NonNull
    private LocalDate startDate;

    @NotEmpty
    @NonNull
    private LocalDate endDate;

    public static ProjectDTO from(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .logo(project.getLogo())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }

    public static List<ProjectDTO> from(List<Project> projects) {
        return projects.stream().map(ProjectDTO::from).collect(Collectors.toList());
    }
}
