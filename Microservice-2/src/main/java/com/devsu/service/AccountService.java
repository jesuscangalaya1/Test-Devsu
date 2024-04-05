package com.devsu.service;

import com.devsu.consumers.dtos.AccountReportDTO;
import com.devsu.dto.request.AccountRequest;
import com.devsu.dto.response.AccountResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AccountService {

    List<AccountReportDTO> generateAccountReport(String fechaInicio, String fechaFin, String cliente);

    List<AccountResponse> getAllAccounts();

    AccountResponse getAccountById(Long id);

    AccountResponse createAccount(AccountRequest accountRequest) throws ExecutionException, InterruptedException;

    AccountResponse updateAccount(AccountRequest accountRequest, Long id);

    void deleteAccount(Long id);

    boolean checkBalance(Long accountId, double amount);


}
