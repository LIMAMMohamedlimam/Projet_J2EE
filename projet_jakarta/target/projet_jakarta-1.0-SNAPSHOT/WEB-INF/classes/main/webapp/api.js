// Retrieves JWT token from local storage
function getAuthenticatedHeaders() {
    const token = localStorage.getItem('jwtToken');
    return {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };
}

// Wrapper function for fetch requests that automatically adds authentication
function fetchWithAuth(url, options = {}) {
    return fetch(url, {
        ...options,
        headers: {
            ...options.headers,
            ...getAuthenticatedHeaders()
        }
    });
}
function logout() {
    localStorage.removeItem('jwtToken');
    window.location.href = '/login';
}