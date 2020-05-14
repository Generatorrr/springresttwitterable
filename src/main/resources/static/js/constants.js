export default {
    REQUIRED_FIELD: 'Required field',
    MIN_3_CHARS_FIELD: 'Min 3 characters',
    MIN_100_CHARS_FIELD: 'Min 100 characters',
    getProjectDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO'}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
        }
    },
    getModuleDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO', projectId}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            projectId
        }
    },
    getTestPlanDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO', moduleId, testMethod}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            testMethod,
            moduleId,
        }
    },
    getRequirementDTO: function({name, description, initialDate, endDate, id = null, status = 'TO_DO', moduleId}) {
        return {
            id,
            name,
            description,
            status,
            initialDate: new Date(initialDate).toISOString(),
            endDate: new Date(endDate).toISOString(),
            moduleId,
        }
    },
}