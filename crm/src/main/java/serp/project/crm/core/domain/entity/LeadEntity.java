/**
 * Author: QuanTuanHuy
 * Description: Part of Serp Project
 */

package serp.project.crm.core.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import serp.project.crm.core.domain.enums.LeadSource;
import serp.project.crm.core.domain.enums.LeadStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class LeadEntity extends BaseEntity {
    private String company;
    private String industry;
    private String companySize;
    private String website;

    private String name;
    private String email;
    private String phone;
    private String jobTitle;

    private AddressEntity address;

    private LeadSource leadSource;
    private LeadStatus leadStatus;

    private Long assignedTo;
    private BigDecimal estimatedValue;
    private Integer probability;

    private LocalDate expectedCloseDate;
    private String notes;

    // Lead qualification
    public boolean isQualified() {
        return LeadStatus.QUALIFIED.equals(this.leadStatus) ||
                LeadStatus.CONVERTED.equals(this.leadStatus);
    }

    public boolean canBeConverted() {
        return LeadStatus.QUALIFIED.equals(this.leadStatus) &&
                estimatedValue != null &&
                estimatedValue.compareTo(BigDecimal.ZERO) > 0;
    }

    public void qualify(Long qualifiedBy, String notes) {
        if (isQualified()) {
            throw new IllegalStateException("Lead is already qualified or converted.");
        }
        this.leadStatus = LeadStatus.QUALIFIED;
        this.notes = notes;
        this.setUpdatedBy(qualifiedBy);
    }

    public void disqualify(Long disqualifiedBy, String notes) {
        if (LeadStatus.DISQUALIFIED.equals(this.leadStatus)) {
            throw new IllegalStateException("Lead is already disqualified.");
        }
        this.leadStatus = LeadStatus.DISQUALIFIED;
        this.notes = notes;
        this.setUpdatedBy(disqualifiedBy);
    }

    // Lead scoring
    public void updateProbability(Integer probability, Long updatedBy) {
        if (probability < 0 || probability > 100) {
            throw new IllegalArgumentException("Probability must be between 0 and 100.");
        }
        this.probability = probability;
        this.setUpdatedBy(updatedBy);
    }

    // Lead assignment
    public void assignTo(Long salesRepId, Long assignedBy) {
        this.assignedTo = salesRepId;
        this.setUpdatedBy(assignedBy);
    }

    public void markAsConverted(Long convertedBy) {
        if (!canBeConverted()) {
            throw new IllegalStateException(
                    "Lead cannot be converted. Ensure it is qualified and has a valid estimated value.");
        }
        this.leadStatus = LeadStatus.CONVERTED;
        this.setUpdatedBy(convertedBy);
    }

    // Helper method
    public boolean isOverdue() {
        return expectedCloseDate != null &&
                expectedCloseDate.isBefore(LocalDate.now()) &&
                !isQualified();
    }
}
