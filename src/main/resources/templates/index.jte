@import dto.UsersPage
@import dto.StartPage
@import utils.NamedRoutes
@param UsersPage page


@template.layout.page(
content = @`
    <main class="flax-grow-1">
        @if(page.getFlash() != null)
            <div class="rounded-0 m-0 alert alert-dismissible fade show alert-warning" role="alert">
                <p class="m-0">${page.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        @endif
        <body >
        <section>
            <div class="container-fluid bg-light p-5">
                <div class="row">
                    <div class="col-md-10 col-lg-8 mx-auto text-dark">
                        <h1 class="display-3 mb-0">QuestGame</h1>
                        <p class="lead">${StartPage.getTitle()}</p>
                        <form action="/" method="post" class="rss-form text-body">
                            <div class="row">
                                <div class="col">
                                    <div class="form-floating">
                                        <input id="name-input" autofocus="" type="text" required="" name="name" aria-label="name"
                                               class="form-control w-80" placeholder="введи имя" autocomplete="off">
                                        <label for="name-input">Введи имя, не менее 4 символов</label>
                                    </div>
                                </div>
                                <div class="col">
                                    <button type="submit" class="h-100 btn btn-lg btn-primary px-sm-3">Далее</button>
                                </div>
                            </div>
                        </form>
                        <div>
                            @if(!page.getUsers().isEmpty())
                            <table class="table table-bordered table-hover mt-3">
                                <thead>
                                <tr>
                                    <th scope="col">id</th>
                                    <th scope="col">Срисок игроков</th>
                                </tr>
                                </thead>
                                @for(var user : page.getUsers())
                                    <tbody>
                                    <tr>
                                        <th scope="row">${user.getId()}</th>
                                        <td><a href="${NamedRoutes.userPath(user.getId())}">${user.getName()}</a></td>
                                    </tr>
                                    </tbody>
                                @endfor
                            </table>
                            @endif
                        </div>
                    </div>
                </div>
            </div>
        </section>
        </body>
    </main>
`
)