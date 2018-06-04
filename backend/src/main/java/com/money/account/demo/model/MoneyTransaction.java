package com.money.account.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = "id",
        allowGetters = true)
public class MoneyTransaction {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name= "increment", strategy= "increment")
    @Column(name = "id", length = 6, nullable = false)
    private long id;

    @Column(name = "transaction_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date transactionDate;

    @NotNull(message = "{remained_balance_blank_error_message}")
    @Column(name = "remained_balance")
    private BigDecimal remainedBalance;

    @NotNull(message = "{operation_sum_blank_error_message}")
    @Column(name = "operation_sum")
    private BigDecimal operationSum;

    @NotNull
    @Column(name = "operation_type")
    private OperationType operationType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public MoneyTransaction() {
    }

    public MoneyTransaction(Date transactionDate,
            @NotBlank BigDecimal remainedBalance, @NotBlank BigDecimal operationSum,
            @NotBlank OperationType operationType,
            @NotBlank User user) {
        this.transactionDate = transactionDate;
        this.remainedBalance = remainedBalance;
        this.operationSum = operationSum;
        this.operationType = operationType;
        this.user = user;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getRemainedBalance() {
        return remainedBalance;
    }

    public void setRemainedBalance(BigDecimal remainedBalance) {
        this.remainedBalance = remainedBalance;
    }

    public BigDecimal getOperationSum() {
        return operationSum;
    }

    public void setOperationSum(BigDecimal operationSum) {
        this.operationSum = operationSum;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        MoneyTransaction that = (MoneyTransaction) o;

        if (id != that.id)
            return false;
        if (transactionDate != null ? !transactionDate.equals(that.transactionDate) : that.transactionDate != null)
            return false;
        if (remainedBalance != null ? !remainedBalance.equals(that.remainedBalance) : that.remainedBalance != null)
            return false;
        if (operationSum != null ? !operationSum.equals(that.operationSum) : that.operationSum != null)
            return false;
        if (operationType != that.operationType)
            return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (transactionDate != null ? transactionDate.hashCode() : 0);
        result = 31 * result + (remainedBalance != null ? remainedBalance.hashCode() : 0);
        result = 31 * result + (operationSum != null ? operationSum.hashCode() : 0);
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MoneyTransaction{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", remainedBalance=" + remainedBalance +
                ", operationSum=" + operationSum +
                ", operationType=" + operationType +
                ", user=" + user +
                '}';
    }
}
